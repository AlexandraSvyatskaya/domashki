import core.Line;
import core.Station;

import java.util.*;
import java.util.stream.Collectors;

public class StationIndex
{
    HashMap<Integer, Line> number2line;
    TreeSet<Station> stations;
    TreeMap<Station, TreeSet<Station>> connections;

    public StationIndex() // метод вызываемый при создании станции
    {
        number2line = new HashMap<>();
        stations = new TreeSet<>();
        connections = new TreeMap<>();
    }

    public void addStation(Station station)
    {
        stations.add(station);
    } // метод добавления станции

    public void addLine(Line line)
    {
        number2line.put(line.getNumber(), line);
    }//метод добавления линии

    public void addConnection(List<Station> stations) //метод добавления перехода
    {
        for(Station station : stations)    //перебераем переданый лист
        {
            if(!connections.containsKey(station)) {   //если станции нет в списке переходов, добавляем эту станцию и новый список в имеющийся
                connections.put(station, new TreeSet<>());
            }
            TreeSet<Station> connectedStations = connections.get(station); //иначе создаем лист со
            connectedStations.addAll(stations.stream()
                .filter(s -> !s.equals(station)).collect(Collectors.toList()));
        }
    }

    public Line getLine(int number)
    {
        return number2line.get(number);
    }

    public Station getStation(String name)
    {
        for(Station station : stations)
        {
            if(station.getName().equalsIgnoreCase(name)) {
                return station;
            }
        }
        return null;
    }

    public Station getStation(String name, int lineNumber)
    {
        Station query = new Station(name, getLine(lineNumber));
        Station station = stations.ceiling(query);
        return station.equals(query) ? station : null;
    }

    public Set<Station> getConnectedStations(Station station)
    {
        if(connections.containsKey(station)) {
            return connections.get(station);
        }
        return new TreeSet<>();
    }
}
