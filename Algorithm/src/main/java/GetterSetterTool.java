package src.main.java;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetterSetterTool {
    public static void generateGettersAndSetters(String folderPath) {
        try {
            Path directoryPath = Path.of(folderPath);

            Files.list(directoryPath)
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        try {
                            processFile(filePath);
                        } catch (IOException e) {
                            System.err.println("Error processing file: " + filePath + ", " + e.getMessage());
                        }
                    });

        } catch (IOException e) {
            System.err.println("Error listing files in directory: " + folderPath + ", " + e.getMessage());
        }
    }

    private static void processFile(Path filePath) throws IOException {
        StringBuilder classContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                classContent.append(line).append("\n");
            }
        }

        Map<String, Object> rsMap = extractFieldsAndGenerateConstructors(classContent.toString());
        String updatedContent01 = (String) rsMap.get("updatedContent");

        writeUpdatedContentToFile(filePath, updatedContent01);
        System.out.println("Getter and setter methods added successfully to the file: " + filePath.getFileName());
    }

    private static Map<String, Object> extractFieldsAndGenerateConstructors(String content) {
        StringBuilder updatedContent = new StringBuilder();
        String[] lines = content.split("\\r?\\n");
        String currentClass = null;
        Map<String, String> map = new HashMap<>();
        List<String> fieldList = new ArrayList<>(); // Danh sách các trường để sử dụng cho constructor
        Pattern variablePattern = Pattern.compile("(\\w+)\\s+(\\w+);");

        boolean hasExtractedFields = false; // Biến để kiểm tra xem lớp hiện tại có trường nào được trích xuất hay không

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("public class")) {
                if (!fieldList.isEmpty() && hasExtractedFields) {
                    generateConstructor(currentClass, fieldList, updatedContent);
                    updatedContent.append("\n");
                }
                if (currentClass != null) {
                    fieldList.clear();
                    hasExtractedFields = false; // Đặt lại biến kiểm tra cho lớp mới
                }
            } else {
                Matcher variableMatcher = variablePattern.matcher(line);
                if (variableMatcher.find()) {
                    String dataType = variableMatcher.group(1); // Kiểu dữ liệu của biến
                    String fieldName = variableMatcher.group(2); // Tên của biến
                    // Tạo getter, setter và thêm vào map
                    fieldList.add(fieldName);
                    hasExtractedFields = true; // Đánh dấu đã trích xuất trường từ lớp hiện tại

                }
            }
            updatedContent.append(line).append("\n");
        }


        Map<String, Object> result = new HashMap<>();
        result.put("updatedContent", updatedContent.toString());
        return result;
    }
    private static Map<String, Object> extractFieldsAndGenerateGettersSetters(String content) {
        StringBuilder updatedContent = new StringBuilder();
        String[] lines = content.split("\\r?\\n");
        String currentClass = null;
        Map<String, String> map = new HashMap<>();
        Pattern variablePattern = Pattern.compile("(\\w+)\\s+(\\w+);");

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("public class")) {
                currentClass = line.substring("public class ".length(), line.indexOf('{')).trim();
            } else {
                Matcher variableMatcher = variablePattern.matcher(line);
                if (variableMatcher.find()) {
                    String dataType = variableMatcher.group(1); // Kiểu dữ liệu của biến
                    String fieldName = variableMatcher.group(2); // Tên của biến
                    String capitalizedName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                    if (generateGetterSetter(dataType, fieldName, capitalizedName, updatedContent)) {
                        // Nếu tạo thành công, thêm vào map
                        String getter = currentClass + ".get" + capitalizedName + "()";
                        String setter = currentClass + ".set" + capitalizedName + "(" + dataType + " " + fieldName + ")";
                        map.put(fieldName, getter + "\n" + setter);
                    }
                }
            }
            updatedContent.append(line).append("\n");
        }

        // Lưu kết quả vào map và trả về
        Map<String, Object> result = new HashMap<>();
        result.put("updatedContent", updatedContent.toString());
        result.put("map", map);
        return result;
    }

    private static boolean generateGetterSetter(String dataType, String fieldName, String capitalizedName, StringBuilder updatedContent) {
        updatedContent.append("public ").append(dataType).append(" get").append(capitalizedName).append("() {\n");
        updatedContent.append("    return ").append(fieldName).append(";\n");
        updatedContent.append("}\n\n");
        updatedContent.append("public void set").append(capitalizedName).append("(").append(dataType).append(" ").append(fieldName).append(") {\n");
        updatedContent.append("    this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
        updatedContent.append("}\n\n");

        return true;
    }

    private static void writeUpdatedContentToFile(Path filePath, String updatedContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(updatedContent);
        }
    }
    private static void generateConstructor(String className, List<String> fieldList, StringBuilder updatedContent) {
        System.out.println("SOON");
    }
}
