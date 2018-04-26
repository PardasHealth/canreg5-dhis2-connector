package ir.pardas.dhis2.connector;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVFile {

    public static CSVParser read(Path file, String... header) throws IOException {
        Reader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));
        return new CSVParser(reader, CSVFormat.DEFAULT
                .withHeader(header)
                .withIgnoreHeaderCase()
                .withTrim());
    }
}
