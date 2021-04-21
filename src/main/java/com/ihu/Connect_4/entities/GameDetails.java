package com.ihu.Connect_4.entities;

import com.ihu.Connect_4.enums.PlayerColor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "game_details")
public class GameDetails {

    @EmbeddedId
    private GameDetailsId id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId("gameId")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId("playerId")
    private Player player;

    @Enumerated(EnumType.STRING)
    private PlayerColor playerColor;

    @Column(unique = true, nullable = false)
    private String uuid;

    public GameDetails() {}

    public GameDetails(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.id = new GameDetailsId(game.getId(), player.getId());
    }
    public GameDetails(Game game, Player player, String uuid) {
        this.game = game;
        this.player = player;
        this.uuid = uuid;
        this.id = new GameDetailsId(game.getId(), player.getId());
    }

    public GameDetails(Game game, Player player, String uuid, PlayerColor playerColor) {
        this.game = game;
        this.player = player;
        this.uuid = uuid;
        this.playerColor = playerColor;
        this.id = new GameDetailsId(game.getId(), player.getId());
    }

    public GameDetailsId getId() {
        return id;
    }

    public void setId(GameDetailsId id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDetails that = (GameDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(game, that.game) && Objects.equals(player, that.player) && playerColor == that.playerColor && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, game, player, playerColor, uuid);
    }
}
