import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Employee {
    public int id;
    public String surname;
    public String name;
    public int number_of_hours;
    public double hourly_rate;
    public int birth_day;
    public int birth_month;
    public int birth_year;
    public int job_day;
    public int job_month;
    public int job_year;

    public int work_done;
    public Double price_amount;
    public int age;
    public Double total_amount;

    public Employee(int id, String surname, String name, int number_of_hours, double hourly_rate, int birth_day,
            int birth_month, int birth_year, int job_day, int job_month, int job_year) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.number_of_hours = number_of_hours;
        this.hourly_rate = hourly_rate;
        this.birth_day = birth_day;
        this.birth_month = birth_month;
        this.birth_year = birth_year;
        this.job_day = job_day;
        this.job_month = job_month;
        this.job_year = job_year;

        this.age = this.calculateAge();
        this.work_done = this.calculateWork();
        this.price_amount = this.calculatePriceAmount();

    }

    private Double calculatePriceAmount() {
        return this.hourly_rate * this.number_of_hours;
    }

    private Integer calculateAge() {
        Date dateNow = new Date();
        int currentYear = dateNow.getYear() + 1900;
        int currentMonth = dateNow.getMonth() + 1;
        int currentDay = dateNow.getDate();

        int age = currentYear - this.birth_year;

        if (currentMonth < this.birth_month || (currentMonth == this.birth_month && currentDay < this.birth_day)) {
            age--;
        }

        return age;
    }

    public static Double calculateTotalAmount(ArrayList<Employee> employees) {
        Double totalAmount = 0.0;
        for (Employee employee : employees) {
            totalAmount += employee.price_amount;
        }
        return totalAmount;
    }

    private Integer calculateWork() {
        Date dateNow = new Date();
        int currentYear = dateNow.getYear() + 1900;
        int currentMonth = dateNow.getMonth() + 1;
        int currentDay = dateNow.getDate();

        int work_done = currentYear - this.job_year;

        // Check if the current month and day are before the birth month and day
        if (currentMonth < this.job_month || (currentMonth == this.job_month && currentDay < this.job_day)) {
            work_done--;
        }

        return work_done;
    }

    public static String toJson(ArrayList<Employee> employees) {
        String json = "[";
        boolean isFirst = true;
        Double totalAmount = 0.0;
        calculateTotalAmount(employees);
        for (Employee employee : employees) {
            if (!isFirst) {
                json += ",";
            }
            isFirst = false;
            json += "{\"id\":" + employee.id + ", \"surname\":\"" + employee.surname + "\", \"name\":\"" + employee.name
                    + "\", \"number_of_hours\":" + employee.number_of_hours + ", \"hourly_rate\":\""
                    + employee.hourly_rate + "\", \"birth_day\":" + employee.birth_day + ", \"birth_month\":"
                    + employee.birth_month + ", \"birth_year\":" + employee.birth_year + ", \"job_day\":"
                    + employee.job_day + ", \"job_month\":" + employee.job_month + ", \"job_year\":" + employee.job_year
                    + ", \"work_done\":\"" + employee.work_done + "\", \"age\":" + employee.age
                    + ", \"price_amount\":\"" + String.format("%.2f", employee.price_amount) + "\"}";
            totalAmount += employee.price_amount;
        }
        json += ",{ \"totalAmount\":" + totalAmount + "}]";

        return json;
    }

    public static ArrayList<Employee> sortBySurname(ArrayList<Employee> employees) {
        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.surname.compareTo(e2.surname);
            }
        });
        return employees;
    }
}
