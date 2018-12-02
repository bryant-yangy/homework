public class Hero {
    private int id;
    private String name;
    private String loc; // location
    private String sex;
    private int birth;
    private int death;
    private int power;
    public Hero(int id, String name, String loc, String sex, int birth, int death, int power) {
        this.id = id;
        this.name = name;
        this.loc = loc;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public String getSex() {
        return sex;
    }

    public int getBirth() {
        return birth;
    }

    public int getDeath() {
        return death;
    }

    public String getLoc() {

        return loc;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loc='" + loc + '\'' +
                ", sex='" + sex + '\'' +
                ", birth=" + birth +
                ", death=" + death +
                ", power=" + power +
                '}';
    }
}
