/*   1:    */ package sh.multiplayer.player;
/*   2:    */ 
/*   3:    */ public class Player
/*   4:    */ {
/*   5:    */   private int playerId;
/*   6:    */   private int connectionId;
/*   7:    */   private String faction;
/*   8:    */   private int team;
/*   9:    */   private int points;
/*  10:    */   private boolean defeated;
/*  11:    */   private int colour;
/*  12:    */   private int deaths;
/*  13:    */   
/*  14:    */   public int getColour()
/*  15:    */   {
/*  16: 14 */     return this.colour;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void setColour(int colour)
/*  20:    */   {
/*  21: 19 */     this.colour = colour;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean isDefeated()
/*  25:    */   {
/*  26: 24 */     return this.defeated;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setDefeated(boolean defeated)
/*  30:    */   {
/*  31: 29 */     this.defeated = defeated;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Player(int playerId, int connectionId, String faction, int team, int points, int deaths)
/*  35:    */   {
/*  36: 37 */     this.playerId = playerId;
/*  37: 38 */     this.connectionId = connectionId;
/*  38: 39 */     this.faction = faction;
/*  39: 40 */     this.team = team;
/*  40: 41 */     this.points = points;
/*  41: 42 */     this.deaths = deaths;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Player() {}
/*  45:    */   
/*  46:    */   public boolean checkDefeated(int kills)
/*  47:    */   {
/*  48: 52 */     if (this.faction.equalsIgnoreCase("marines"))
/*  49:    */     {
/*  50: 54 */       if (this.points == this.deaths)
/*  51:    */       {
/*  52: 56 */         this.defeated = true;
/*  53: 57 */         return true;
/*  54:    */       }
/*  55:    */     }
/*  56: 62 */     else if (kills == this.deaths)
/*  57:    */     {
/*  58: 64 */       this.defeated = true;
/*  59: 65 */       return true;
/*  60:    */     }
/*  61: 68 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getPlayerId()
/*  65:    */   {
/*  66: 73 */     return this.playerId;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setPlayerId(int playerId)
/*  70:    */   {
/*  71: 78 */     this.playerId = playerId;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getConnectionId()
/*  75:    */   {
/*  76: 83 */     return this.connectionId;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setConnectionId(int connectionId)
/*  80:    */   {
/*  81: 88 */     this.connectionId = connectionId;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getFaction()
/*  85:    */   {
/*  86: 93 */     return this.faction;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setFaction(String faction)
/*  90:    */   {
/*  91: 98 */     this.faction = faction;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getTeam()
/*  95:    */   {
/*  96:103 */     return this.team;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setTeam(int team)
/* 100:    */   {
/* 101:108 */     this.team = team;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getPoints()
/* 105:    */   {
/* 106:113 */     return this.points;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setPoints(int points)
/* 110:    */   {
/* 111:118 */     this.points = points;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getDeaths()
/* 115:    */   {
/* 116:123 */     return this.deaths;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setDeaths(int deaths)
/* 120:    */   {
/* 121:128 */     this.deaths = deaths;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.multiplayer.player.Player
 * JD-Core Version:    0.7.0.1
 */