package com.example.football.models.dto.xmldtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayersSeedXmlRootDto {

    @XmlElement(name = "player")
    List<PlayerSeedXmlDto> players;

    public List<PlayerSeedXmlDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerSeedXmlDto> players) {
        this.players = players;
    }
}
