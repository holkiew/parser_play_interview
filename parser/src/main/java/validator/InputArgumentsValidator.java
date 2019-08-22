package validator;

import lombok.Getter;
import model.asn1.datatypes.CdrType;
import model.pojo.InputArguments;
import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputArgumentsValidator {

    private String inputDir;
    private String minCdrDate;
    private LocalDateTime minCdrLocalDate;
    private String cdrType;
    private String msisdnRegex;
    private String imsiRegex;

    @Getter
    private boolean isValid;
    @Getter
    private String reason;

    public InputArgumentsValidator(String[] args) {
        if (args.length == 6) {
            args = clearInputFromSpecialSigns(args);
            this.inputDir = args[0];
            this.minCdrDate = StringUtils.join(args[1]," ", args[2]);
            this.cdrType = args[3];
            this.msisdnRegex = args[4];
            this.imsiRegex = args[5];
            validateArguments();
        } else {
            reason = "Wrong amount of arguments: <input_dir> <min_cdr_date> <cdr_type> <msisdn_regex> <imsi_regex>";
            isValid = false;
        }
    }

    private String[] clearInputFromSpecialSigns(String[] args) {
        List<String> clearedArgs = Stream.of(args).map(arg -> arg.replaceAll("['\"]", "")).collect(Collectors.toList());
        args = clearedArgs.toArray(new String[0]);
        return args;
    }

    public InputArguments createValidInputArguments() {
        InputArguments inputArguments = new InputArguments();
        inputArguments.setInputDir(inputDir);
        inputArguments.setMinCdrDate(minCdrLocalDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        Optional<CdrType.CdrTypes> first = Stream.of(CdrType.CdrTypes.values()).filter(cdrTypes -> cdrTypes.name.equals(cdrType)).findFirst();
        inputArguments.setCdrType(first.get());
        inputArguments.setImsiRegex(imsiRegex);
        inputArguments.setMsisdnRegex(msisdnRegex);
        return inputArguments;
    }

    private void validateArguments() {
        StringBuilder sb = new StringBuilder();
        validateDirectory(sb);
        validateTimestamp(sb);
        validateCdrType(sb);
        validateRegex(sb, msisdnRegex);
        validateRegex(sb, imsiRegex);
        reason = sb.toString();
        isValid = reason.isEmpty();
    }

    private void validateDirectory(StringBuilder sb) {
        if (!Files.isDirectory(Paths.get(inputDir))) {
            sb.append("Non existing directory was provided\n");
        }
    }

    private void validateRegex(StringBuilder sb, String regex) {
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            sb.append("Wrong regex\n");
        }
    }

    private void validateTimestamp(StringBuilder sb) {
        try {
            minCdrLocalDate = LocalDateTime.parse(minCdrDate, DateTimeFormatter.ofPattern(Utils.DATE_FORMAT));
        } catch (DateTimeParseException ex) {
            sb.append("Wrong date format: yyyy-MM-dd HH:mm:ss\n");
        }
    }

    private void validateCdrType(StringBuilder sb) {
        boolean nonMatch = Stream.of(CdrType.CdrTypes.values()).noneMatch(cdrTypes -> cdrTypes.name.equals(cdrType));
        if (nonMatch) {
            sb.append("Wrong cdr_type\n");
        }
    }
}
