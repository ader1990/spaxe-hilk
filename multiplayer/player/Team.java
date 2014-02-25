/*  1:   */ package sh.multiplayer.player;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class Team
/*  6:   */ {
/*  7:   */   ArrayList<Player> players;
/*  8:   */   int teamNumber;
/*  9:   */   
/* 10:   */   public int getTeamNumber()
/* 11:   */   {
/* 12:12 */     return this.teamNumber;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void setTeamNumber(int teamNumber)
/* 16:   */   {
/* 17:17 */     this.teamNumber = teamNumber;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Team()
/* 21:   */   {
/* 22:22 */     this.players = new ArrayList();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void addPlayer(Player player)
/* 26:   */   {
/* 27:27 */     this.players.add(player);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ArrayList<Player> getPlayers()
/* 31:   */   {
/* 32:32 */     return this.players;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setPlayers(ArrayList<Player> players)
/* 36:   */   {
/* 37:37 */     this.players = players;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.multiplayer.player.Team
 * JD-Core Version:    0.7.0.1
 */