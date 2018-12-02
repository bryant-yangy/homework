import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 1. 找到武将中武力前三的hero对象, 提示流也可以排序
// 2. 按出生地分组
// 3. 找出寿命前三的武将
// 4. 女性寿命最高的
// 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
// 6. 按各个年龄段分组： 0~20, 2140, 41~60, 60以上
// 7. 按武力段分组： >=90， 80~89, 70~79, <70
// 8. 按出生地分组后，统计各组人数
public class TestHero {
    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("D:\\JAVA\\homework\\day21\\heroes.txt"), Charset.forName("utf-8"));
        List<Hero> list = new ArrayList<>();
        lines.forEach(str->{
            String[] split = str.split("\t");
            list.add(new Hero(new Integer(split[0]),split[1],split[2],split[3],new Integer(split[4]),new Integer(split[5]),new Integer(split[6])));
        });
        System.out.println("使用流\n\n\n");
        method1(list);   //流
        System.out.println("\n\n\n==================\n\n\n");
        System.out.println("使用普通方法");
        method2(list);       //普通方法

    }


    private static void method2(List<Hero> list) {
        System.out.println("武力值");
        Collections.sort(list,(a,b)-> b.getPower()-a.getPower());
        int power = list.get(2).getPower();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getPower()<power)
                break;
            System.out.println(list.get(i));
        }
         System.out.println("\n\n\n\n寿命最长三个武将");
        Collections.sort(list,(a,b)->(b.getDeath()-b.getBirth()) -(a.getDeath()-a.getBirth()));
        for (int i = 0; i < 3; i++) {
                System.out.println(list.get(i));
        }
         System.out.println("\n\n\n\n\n女性寿命最高");
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getSex().equals("女"))
            {
                System.out.println(list.get(i));
                break;
            }
        }
        System.out.println("\n\n\n\n\n\n出生地分组并统计人数");
        Map<String, List<Hero>> map = new HashMap<>();
        for(Hero hero:list){
            String loc = hero.getLoc();
            List<Hero> heroes = map.get(loc);
            if(heroes==null){
                heroes=new ArrayList<Hero>();
                map.put(loc,heroes);
            }
            heroes.add(hero);
        }
        map.forEach((key,values)->{
            System.out.println(key+"   人数:"+values.size());
            values.forEach(s->System.out.println("   "  +s));
        });
        System.out.println("\n\n\n\n年龄段分组");
        Map<String, List<Hero>> map1 = new HashMap<>();
        for(Hero hero:list){
            String age = findgroup1(hero);
            List<Hero> heroes = map1.get(age);
            if(heroes==null){
                heroes=new ArrayList<Hero>();
                map1.put(age,heroes);
            }
            heroes.add(hero);
        }
        map1.forEach((key,values)->{
            System.out.println(key+"   人数:"+values.size());
            values.forEach(s->System.out.println("   "  +s));
        });
        System.out.println("\n\n\n\n武力值段分组");
        Map<String, List<Hero>> map2 = new HashMap<>();
        for(Hero hero:list){
            String powers = findgroup2(hero);
            List<Hero> heroes = map2.get(powers);
            if(heroes==null){
                heroes=new ArrayList<Hero>();
                map2.put(powers,heroes);
            }
            heroes.add(hero);
        }
        map2.forEach((key,values)->{
            System.out.println(key+"   人数:"+values.size());
            values.forEach(s->System.out.println("   "  +s));
        });
    }



    private static void method1(List<Hero> list) {
        System.out.println("武力值");
        list.stream().sorted((a,b)-> b.getPower()-a.getPower()).limit(3).forEach(a->System.out.println(a));
        System.out.println("\n\n\n\n出生地分组并统计人数");
        Map<String, List<Hero>> collect = list.stream().collect(Collectors.groupingBy((s) -> s.getLoc()));
        collect.forEach((key,values)->{
            System.out.println(key+"   人数:"+values.size());
            values.forEach(s->System.out.println("   "  +s));
        });
        System.out.println("\n\n\n\n寿命最长三个武将");
        list.stream().sorted((a,b)->(b.getDeath()-b.getBirth())
                -(a.getDeath()-a.getBirth())).limit(3).forEach(a->System.out.println(a));
        System.out.println("\n\n\n\n女性寿命最高");
        list.stream().filter(a->a.getSex().equals("女")).sorted((a,b)->(b.getDeath()-b.getBirth())
                -(a.getDeath()-a.getBirth())).limit(1).forEach(a->System.out.println(a));
        System.out.println("\n\n\n\n武力排名前三");
        List<Hero> collect1 = list.stream().sorted((a, b) -> b.getPower() - a.getPower()).collect(Collectors.toList());
        int power = collect1.get(2).getPower();
        collect1.stream().filter(a->a.getPower()>=power).forEach(a->System.out.println(a));
        System.out.println("\n\n\n\n年龄段分组");
        Map<String, List<Hero>> collect2 = list.stream().collect(Collectors.groupingBy(TestHero::findgroup1));
        collect2.forEach((key,values)->{
            System.out.println(key+"   人数:"+values.size());
            values.forEach(s->System.out.println("   "  +s));
        });
        System.out.println("\n\n\n\n武力值段分组");
        Map<String, List<Hero>> collect3 = list.stream().collect(Collectors.groupingBy(TestHero::findgroup2));
        collect3.forEach((key,values)->{
            System.out.println(key+"   人数:"+values.size());
            values.forEach(s->System.out.println("   "  +s));
        });
    }
    public static String findgroup1(Hero hero){
        int age=hero.getDeath()-hero.getBirth();
        if(age<20)
            return "0~20";
        else if(age<40)
            return "21~40";
        else if(age<60)
            return "41~60";
        else return "60以上";
    }
    public static String findgroup2(Hero hero){
        int power= hero.getPower();
        if(power>90)
            return "90以上";
        else if(power>80)
            return "80~89";
        else if(power>70)
            return "70~79";
        else return "70以下";

    }
}
