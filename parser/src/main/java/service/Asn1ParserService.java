package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matcher.CdrHlrMatcher;
import model.asn1.cdr.hlr.version3.CDRHLRVERSION3;
import model.pojo.HrlPojo;
import model.pojo.InputArguments;
import org.apache.commons.io.FileUtils;
import util.Utils;
import validator.InputArgumentsValidator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Asn1ParserService {
    private ObjectMapper objectMapper;

    public Asn1ParserService() {
        init();
    }

    private void init() {
        this.objectMapper = new ObjectMapper();
    }

    public Optional<List<CDRHLRVERSION3>> parseAsn1(String[] args) {
        InputArgumentsValidator inputArgumentsValidator = new InputArgumentsValidator(args);
        if (isArgsNotValid(inputArgumentsValidator)) {
            return Optional.empty();
        }
        InputArguments validInputArguments = inputArgumentsValidator.createValidInputArguments();

        List<CDRHLRVERSION3> matchingFiles = findMatchingFiles(validInputArguments);

        return Optional.of(matchingFiles);
    }

    public void printResults(List<CDRHLRVERSION3> hlrsMatched) {
        hlrsMatched.stream().map(HrlPojo::new).forEach(hrlPojo -> {
            try {
                String json = objectMapper.writeValueAsString(hrlPojo);
                System.out.println(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isArgsNotValid(InputArgumentsValidator inputArgumentsValidator) {
        if (inputArgumentsValidator.isValid()) {
            return false;
        }
        System.out.println(inputArgumentsValidator.getReason());
        System.exit(1);
        return true;
    }

    private List<CDRHLRVERSION3> findMatchingFiles(InputArguments inputArguments) {
        List<CDRHLRVERSION3> hlrsMatched = new ArrayList<>();

        CdrHlrMatcher matcher = new CdrHlrMatcher(inputArguments);

        filterFiles(hlrsMatched, inputArguments, matcher);
        return hlrsMatched;
    }

    private void filterFiles(List<CDRHLRVERSION3> hlrsMatched, InputArguments validInputArguments, CdrHlrMatcher matcher) {
        Iterator it = FileUtils.iterateFiles(new File(validInputArguments.getInputDir()), new String[]{Utils.BER_FORMAT}, false);
        while (it.hasNext()) {
            try {
                CDRHLRVERSION3 hlr = createCdrHlr(it);
                if (matcher.isMatching(hlr)) {
                    hlrsMatched.add(hlr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private CDRHLRVERSION3 createCdrHlr(Iterator it) throws IOException {
        byte[] byteBuffer = FileUtils.readFileToByteArray((File) it.next());
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer);
        CDRHLRVERSION3 cdrhlrversion3 = new CDRHLRVERSION3();
        cdrhlrversion3.decode(byteArrayInputStream);
        return cdrhlrversion3;
    }
}
