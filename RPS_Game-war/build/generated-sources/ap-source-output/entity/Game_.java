package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-01T02:18:47")
@StaticMetamodel(Game.class)
public class Game_ { 

    public static volatile SingularAttribute<Game, String> winner;
    public static volatile SingularAttribute<Game, String> playerA;
    public static volatile SingularAttribute<Game, String> playerB;
    public static volatile SingularAttribute<Game, Long> id;
    public static volatile SingularAttribute<Game, Boolean> draw;

}