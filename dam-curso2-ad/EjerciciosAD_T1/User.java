package EjerciciosAD_T1;
public class User {
    static int createdUsers = 0;
    private int userNumber = 0;
    private String name;
    private String lastName;
    private String phone;
    private String id;
    private int age;

    public User(String name, String lastName, String phone, String id, int age) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.id = id;
        this.age = age;
        createdUsers++;
        this.userNumber = createdUsers;
    }

    public User() {
        createdUsers++;
    }


    public static int getCreatedUsers() {
        return createdUsers;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario: " + this.userNumber);
        sb.append("\n- Nombre: " + this.name);
        sb.append("\n- Apellido: " + this.lastName);
        sb.append("\n- Teléfono: " + this.phone);
        sb.append("\n- DNI: " + this.id);
        sb.append("\n- Edad: " + String.valueOf(this.getAge()));
        return sb.toString();
    }
}
