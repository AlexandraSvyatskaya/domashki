import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RouteCalculatorTest extends TestCase {
    //Схема станций
    //Линия 1        Линия 2       Линия 3

    //Станция1
    //Станция2------Станция4
    //Станция3      Станция5
    //              Станция6-----Станция8
    //              Станция7     Станция9
    List<Station> route1;
    List<Station> route2;
    List<Station> route3;
    StationIndex stationIndexTest;
    RouteCalculator calculator;
    Station one;
    Station three;
    Station five;
    Station eight;

    @Override
    protected void setUp() throws Exception {
        stationIndexTest = new StationIndex();
        Line line1 = new Line(1,"Первая линия");
        Line line2 = new Line(2,"Вторая линия");
        Line line3 = new Line(3,"Третья линия");
         one = new Station("ПерваяСтанция", line1);
        Station two = new Station("ВтораяСтанция",line1);
        three = new Station("ТретьяСтанция",line1);
        Station four = new Station("ЧетвертаяСтанция",line2);
        five = new Station("ПятаяСтанция",line2);
        Station six = new Station("ШестаяСтанция",line2);
        Station seven = new Station("СедьмаяСтанция",line2);
        eight = new Station("ВосьмаяСтанция",line3);
        Station nine = new Station("ДевятаяСтанция",line3);
        Stream.of(line1,line2,line3).forEach(stationIndexTest::addLine);
        Stream.of(one,two,three,four,five,six,seven,eight,nine).peek(s -> s.getLine().addStation(s)).forEach(stationIndexTest::addStation);
        List<Station> connection1 = new ArrayList<>();
        connection1.add(stationIndexTest.getStation("ВтораяСтанция",1));
        connection1.add(stationIndexTest.getStation("ЧетвертаяСтанция",2));
        List<Station> connection2 = new ArrayList<>();
        connection2.add(stationIndexTest.getStation("ШестаяСтанция",2));
        connection2.add(stationIndexTest.getStation("ВосьмаяСтанция",3));
        stationIndexTest.addConnection(connection1);
        stationIndexTest.addConnection(connection2);
        calculator = new RouteCalculator(stationIndexTest);


        route1 = Stream.of(one,two,three).collect(Collectors.toList());
        route2 = Stream.of(one,two,four,five).collect(Collectors.toList());
        route3 = Stream.of(three,two,four,five,six,eight).collect(Collectors.toList());



    }
    public void testCalculateDuration(){
        double actual = RouteCalculator.calculateDuration(route1);
        double expected = 5.0;
        assertEquals(expected,actual);
        double actual2 = RouteCalculator.calculateDuration(route2);
        double expected2 = 8.5;
        assertEquals(expected2,actual2);
        double  actual3 = RouteCalculator.calculateDuration(route3);
        double expected3 = 14.5;
        assertEquals(expected3,actual3);
    }
    public void testGetShortestRoute(){
     List<Station> actual1 = calculator.getShortestRoute(one,three);
     List<Station> actual2 = calculator.getShortestRoute(one,five);
     List<Station> actual3 = calculator.getShortestRoute(three,eight);
     List<Station> expected1 = route1;
     List<Station> expected2 = route2;
     List<Station> expected3 = route3;
    assertEquals(expected1,actual1);
     assertEquals(expected2,actual2);
     assertEquals(expected3,actual3);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
