package model;

//CREATE TABLE Employee
//        (
//        SIN         INTEGER,
//        phone_num   INTEGER,
//        hourly_wage INTEGER,
//        name        VARCHAR(100),
//        PRIMARY KEY (SIN)
//        );
public class Employee {
    private final int SIN;
    private final int phone_num;
    private final int hourly_wage;
    private final String name;

    public Employee(int SIN, int phone_num, int hourly_wage, String name) {
        this.SIN = SIN;
        this.phone_num = phone_num;
        this.hourly_wage = hourly_wage;
        this.name = name;
    }

    public int getSIN() {
        return SIN;
    }

    public int getPhone_num() {
        return phone_num;
    }

    public int getHourly_wage() {
        return hourly_wage;
    }

    public String getName() {
        return name;
    }
}
