import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BringTable {
    public static void bringTheTable() {
        String csvFile = "/Users/gorkemkaramolla/Documents/JavaWebProjects/java-project-maribor/java/data.csv";
        String line;
        String csvSeparator = ";";
        ArrayList<Employee> rows = new ArrayList<>();
        boolean first = true;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(csvSeparator);

                // if (data.length < 11) {

                // continue;
                // }

                int id = Integer.parseInt(data[0]);
                String surname = data[1];
                String name = data[2];
                int number_of_hours = Integer.parseInt(data[3]);
                double hourly_rate = Double.parseDouble(data[4]);
                int birth_day = Integer.parseInt(data[5]);
                int birth_month = Integer.parseInt(data[6]);
                int birth_year = Integer.parseInt(data[7]);
                int job_day = Integer.parseInt(data[8]);
                int job_month = Integer.parseInt(data[9]);
                int job_year = Integer.parseInt(data[10]);

                Employee employee = new Employee(id, surname, name, number_of_hours,
                        hourly_rate,
                        birth_day, birth_month, birth_year,
                        job_day, job_month, job_year);

                rows.add(employee);
            }
            rows = Employee.sortBySurname(rows);
            System.out.println(
                    Employee.toJson(rows));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
