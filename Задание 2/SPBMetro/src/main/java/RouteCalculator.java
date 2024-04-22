import core.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RouteCalculator
{
    private StationIndex stationIndex;

    private static double interStationDuration = 2.5;
    private static double interConnectionDuration = 3.5;

    public RouteCalculator(StationIndex stationIndex)
    {
        this.stationIndex = stationIndex;
    }

    public List<Station> getShortestRoute(Station from, Station to) // основной метод включет в себя другие
    {
        List<Station> route = getRouteOnTheLine(from, to);  //запускает метод, когда станции на одной линии
        if(route != null) {
            return route;
        }

        route = getRouteWithOneConnection(from, to); //зпусакет метод с одним переходом
        if(route.size() != 0) {
            return route;
        }

        route = getRouteWithTwoConnections(from, to); // запускает метод с двумя переходами
        return route;
    }

    public static double calculateDuration(List<Station> route) //считает время пути
    {
        double duration = 0;
        Station previousStation = null;
        for(int i = 0; i < route.size(); i++)
        {
            Station station = route.get(i);
            if(i > 0)
            {
                duration += previousStation.getLine().equals(station.getLine()) ?
                    interStationDuration : interConnectionDuration;
            }
            previousStation = station;
        }
        return duration;
    }

    //=========================================================================

    private List<Station> getRouteOnTheLine(Station from, Station to) // метод на случай если станции прибытия и отправления находятся на одной линии
    {
        if(!from.getLine().equals(to.getLine())) {
            return null;
        }
        ArrayList<Station> route = new ArrayList<>();
        List<Station> stations = from.getLine().getStations();
        int direction = 0;
        for(Station station : stations) //цикл перебора станций на линии
        {
            if(direction == 0)
            {
                if(station.equals(from)) { //если станция совпала с отправленем
                    direction = 1;
                } else if(station.equals(to)) { //если станция совпала с прибытием
                    direction = -1;
                }
            }

            if(direction != 0) { //если было совпадения доавляем в путь станцию
                route.add(station);
            }

            if((direction == 1 && station.equals(to)) ||  //останавлием цикл если станция совпала с отправлением
                (direction == -1 && station.equals(from))) { //или прибытием
                break;
            }
        }
        if(direction == -1) {
            Collections.reverse(route);
        }
        return route;
    }

    private List<Station> getRouteWithOneConnection(Station from, Station to) //метод возвращает лист с маршрутом
    {
        if(from.getLine().equals(to.getLine())) { //если линия отправления и прибытия совпадают
            return null;                        //возвращаем null
        }

        ArrayList<Station> route = new ArrayList<>(); //если нет создаем лист

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for(Station srcStation : fromLineStations) //перебираем станции в линии отправления
        {
            for(Station dstStation : toLineStations) //перебираем станции в линии прибытия
            {
                if(isConnected(srcStation, dstStation)) //если найден переход
                {
                    ArrayList<Station> way = new ArrayList<>(); //создаем лист
                    way.addAll(getRouteOnTheLine(from, srcStation));// добавляем в лист станции от отправления до перехода
                    way.addAll(getRouteOnTheLine(dstStation, to)); //добавляем в лист станции от перехода до назначения
                    if(route.isEmpty() || route.size() > way.size()) //если путь имеет нулевое значение или его размер больше чем у нового пути
                    {                                           //то очищаем этот лист и добавлем туда актуальный
                        route.clear();
                        route.addAll(way);
                    }
                }
            }

        }

        return route;
    }

    private boolean isConnected(Station station1, Station station2) //метод проверяющий наличие перехода
    {
        Set<Station> connected = stationIndex.getConnectedStations(station1);
        return connected.contains(station2);
    }

    private List<Station> getRouteViaConnectedLine(Station from, Station to) //метод возвращает две станции, где можно перейти либо ноль если переход не найден
    {
        Set<Station> fromConnected = stationIndex.getConnectedStations(from); //создаем списки
        Set<Station> toConnected = stationIndex.getConnectedStations(to);
        for(Station srcStation : fromConnected) //цикл перебора станций пока не найдется совпадения линий
        {
            for(Station dstStation : toConnected)
            {
                if(srcStation.getLine().equals(dstStation.getLine())) {
                    return getRouteOnTheLine(srcStation, dstStation);
                }
            }
        }
        return null;
    }

    private List<Station> getRouteWithTwoConnections(Station from, Station to)
    {
        if (from.getLine().equals(to.getLine())) { //проверяет совпадет ли линия прибытия и отправления, если да то возвращяет ноль
            return null;
        }

        ArrayList<Station> route = new ArrayList<>(); //создаем лист для станций

        List<Station> fromLineStations = from.getLine().getStations(); //создаем лист для станций линии отправления
        List<Station> toLineStations = to.getLine().getStations(); //создаем лист для станций линии прибытия
        for(Station srcStation : fromLineStations) //перебираем в цикле станции с линии
        {
            for (Station dstStation : toLineStations)
            {
                List<Station> connectedLineRoute =
                    getRouteViaConnectedLine(srcStation, dstStation); //вызываем метод, который возвращает переход
                if(connectedLineRoute == null) {                      //елси перехода нет, выходим из цикла и возвращаем пустой лист с путем
                    continue;
                }
                ArrayList<Station> way = new ArrayList<>();   //если переход найден заполняем лист станциями
                way.addAll(getRouteOnTheLine(from, srcStation));
                way.addAll(connectedLineRoute);
                way.addAll(getRouteOnTheLine(dstStation, to));
                if(route.isEmpty() || route.size() > way.size())
                {
                    route.clear();
                    route.addAll(way);
                }
            }
        }

        return route;
    }
}