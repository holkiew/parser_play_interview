# parser_play_interviewZadanie:

Implementacja parsera plików ASN.1.
Opis:
W katalogu asn znajdują się pliki ASN.1 opisujące strukturę obiektu CDR-HLR w
wersji 3. Zadanie polega na stworzeniu parsera plików zakodowanych w
formacie BER zawierających obiekty CDR-HLR, pozwalającego na filtrowanie
tych obiektów w oparciu o zadane kryteria.
W katalogu cdr_files znajdują się przykładowe pliki .ber zawierające
zakodowane obiekty cdr, które można wykorzystać do testów rozwiązania.
Interfejs wejściowy:
Aplikacja będzie uruchamiana z wiersza poleceń poprzez następujące
wywołanie:
java -jar <jar_file> <input_dir> <min_cdr_date> <cdr_type> <msisdn_regex>
<imsi_regex>
Poniżej znajduje się opis kolejnych argumentów:
• jar_file – wykonalny plik jar parsera
• input_dir – ścieżka do katalogu zawierającego pliki do przetworzenia
przez parser
• min_cdr_date – minimalna wartość pola timestamp obiektu cdr
przekazywana w formacie yyyy-MM-dd HH:mm:ss
• cdr_type – typ obiektu cdr, możliwe wartości: updateLocation,
cancelLocation
• msisdn_regex – wyrażenie regularne stanowiące filtr dla obiektów cdr w
oparciu o wartości pola msisdn
• imsi_regex – wyrażenie regularne stanowiące filtr dla obiektów cdr w
oparciu o wartości pola imsi
Interfejs wyjściowy:
Aplikacja powinna drukować na standardowe wyjście obiekty cdr, które
spełniają zadane kryteria, zapisane w formacie json.
Przykładowe wywołanie i wydruk:
java -jar CdrHlrParser.jar 'cdr_files' '2017-12-11 00:00:00' 'updateLocation'
'48\d{9}' '.*'
{"imsi":"260060018942143","type":"updateLocation","msisdn":"48652180181","vlr":"
1","timestamp":"2017-12-11 00:56:18"}
{"imsi":"260060011564227","type":"updateLocation","msisdn":"48790054321","vlr":"
4","timestamp":"2017-12-12 23:16:58"}
