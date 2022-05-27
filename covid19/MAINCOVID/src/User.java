public class User {
    private String name;
    private int id; 
    private String lastname;
    private String gender;

    public User(int id,String name , String lastname, int age,String gender ){
        this.name = name;
        this.id = id;
        this.lastname = lastname;
        this.gender = gender ;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public String getLastname(){
        return lastname;
    }

    public String getGender(){
        return gender;
    }
}
