/*   1:    */ package sh.world;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.newdawn.slick.tiled.TiledMap;
/*   6:    */ import sh.agent.AgentModel;
/*   7:    */ import sh.agent.door.DoorModel;
/*   8:    */ import sh.agent.fire.FireModel;
/*   9:    */ import sh.gameobject.GameModel;
/*  10:    */ import sh.gameobject.startposition.StartPositionModel;
/*  11:    */ 
/*  12:    */ public class TileBasedWorldModel
/*  13:    */   extends WorldModel
/*  14:    */ {
/*  15:    */   protected TiledMap map;
/*  16:    */   
/*  17:    */   public TileBasedWorldModel(TiledMap map)
/*  18:    */   {
/*  19: 23 */     this.map = map;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected boolean checkTileProperty(int xTile, int yTile, String property, String value)
/*  23:    */   {
/*  24: 27 */     for (int l = 0; l < this.map.getLayerCount(); l++)
/*  25:    */     {
/*  26: 28 */       int tileID = this.map.getTileId(xTile, yTile, l);
/*  27:    */       
/*  28: 30 */       String tileProperty = this.map.getTileProperty(tileID, property, null);
/*  29: 32 */       if ((tileProperty != null) && (tileProperty.equals(value))) {
/*  30: 33 */         return true;
/*  31:    */       }
/*  32:    */     }
/*  33: 37 */     return false;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getHeightInTiles()
/*  37:    */   {
/*  38: 41 */     return this.map.getHeight();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getWidthInTiles()
/*  42:    */   {
/*  43: 45 */     return this.map.getWidth();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getTileWidth()
/*  47:    */   {
/*  48: 49 */     return this.map.getTileWidth();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getTileHeight()
/*  52:    */   {
/*  53: 53 */     return this.map.getTileHeight();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean getFireOnTile(float x, float y)
/*  57:    */   {
/*  58: 58 */     x += 0.5F;
/*  59: 59 */     y += 0.5F;
/*  60: 60 */     for (AgentModel b : this.agentModels.values()) {
/*  61: 62 */       if ((b.getX() == x) && (b.getY() == y)) {
/*  62: 64 */         if ((b instanceof FireModel)) {
/*  63: 66 */           return true;
/*  64:    */         }
/*  65:    */       }
/*  66:    */     }
/*  67: 71 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public AgentModel getAgentOnTile(float x, float y)
/*  71:    */   {
/*  72: 77 */     if (x == Math.round(x)) {
/*  73: 79 */       x += 0.5F;
/*  74:    */     }
/*  75: 81 */     if (y == Math.round(y)) {
/*  76: 83 */       y += 0.5F;
/*  77:    */     }
/*  78: 87 */     for (AgentModel b : this.agentModels.values()) {
/*  79: 91 */       if ((b.getX() == x) && (b.getY() == y)) {
/*  80: 94 */         if ((b instanceof DoorModel))
/*  81:    */         {
/*  82: 97 */           DoorModel d = (DoorModel)b;
/*  83: 99 */           if (!d.isOpen()) {
/*  84:102 */             return b;
/*  85:    */           }
/*  86:    */         }
/*  87:    */         else
/*  88:    */         {
/*  89:107 */           return b;
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:112 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public GameModel getGameObjectOnTile(float x, float y)
/*  97:    */   {
/*  98:117 */     if (x == Math.round(x)) {
/*  99:119 */       x += 0.5F;
/* 100:    */     }
/* 101:121 */     if (y == Math.round(y)) {
/* 102:123 */       y += 0.5F;
/* 103:    */     }
/* 104:127 */     for (GameModel b : this.gameModels.values()) {
/* 105:131 */       if ((b.getX() == x) && (b.getY() == y)) {
/* 106:134 */         return b;
/* 107:    */       }
/* 108:    */     }
/* 109:138 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public StartPositionModel getStartPositionOnTile(float x, float y)
/* 113:    */   {
/* 114:143 */     if (x == Math.round(x)) {
/* 115:145 */       x += 0.5F;
/* 116:    */     }
/* 117:147 */     if (y == Math.round(y)) {
/* 118:149 */       y += 0.5F;
/* 119:    */     }
/* 120:153 */     for (GameModel b : this.gameModels.values()) {
/* 121:156 */       if ((b instanceof StartPositionModel)) {
/* 122:158 */         if ((b.getX() == x) && (b.getY() == y)) {
/* 123:161 */           return (StartPositionModel)b;
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:166 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean getStartPositionOnTile(int player, float x, float y)
/* 131:    */   {
/* 132:171 */     if (x == Math.round(x)) {
/* 133:173 */       x += 0.5F;
/* 134:    */     }
/* 135:175 */     if (y == Math.round(y)) {
/* 136:177 */       y += 0.5F;
/* 137:    */     }
/* 138:181 */     for (GameModel b : this.gameModels.values()) {
/* 139:184 */       if ((b instanceof StartPositionModel)) {
/* 140:186 */         if ((b.getX() == x) && (b.getY() == y)) {
/* 141:188 */           if ((((StartPositionModel)b).isOpen()) && (((StartPositionModel)b).getPlayerId() == player)) {
/* 142:190 */             return true;
/* 143:    */           }
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:195 */     return false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public ArrayList<StartPositionModel> getStartPositions(int player)
/* 151:    */   {
/* 152:200 */     ArrayList<StartPositionModel> models = new ArrayList();
/* 153:202 */     for (GameModel b : this.gameModels.values()) {
/* 154:205 */       if ((b instanceof StartPositionModel)) {
/* 155:207 */         if (((StartPositionModel)b).getPlayerId() == player) {
/* 156:210 */           models.add((StartPositionModel)b);
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:215 */     return models;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public ArrayList<StartPositionModel> getOpenAlienStartPositions()
/* 164:    */   {
/* 165:221 */     ArrayList<StartPositionModel> models = new ArrayList();
/* 166:223 */     for (GameModel b : this.gameModels.values()) {
/* 167:226 */       if ((b instanceof StartPositionModel)) {
/* 168:228 */         if ((((StartPositionModel)b).getFaction() != "marines") && (((StartPositionModel)b).isOpen())) {
/* 169:231 */           models.add((StartPositionModel)b);
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:236 */     return models;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public float[] getTile(float x, float y)
/* 177:    */   {
/* 178:241 */     return new float[] { x / getTileWidth(), y / getTileHeight() };
/* 179:    */   }
/* 180:    */   
/* 181:    */   public float[] getAbsolutePosition(float x, float y)
/* 182:    */   {
/* 183:246 */     return new float[] { x * getTileWidth(), y * getTileHeight() };
/* 184:    */   }
/* 185:    */   
/* 186:    */   public float[] getAbsolutePosition(float[] x)
/* 187:    */   {
/* 188:251 */     return new float[] { x[0] * getTileWidth(), x[1] * getTileHeight() };
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean tileBlocked(float xTile, float yTile)
/* 192:    */   {
/* 193:255 */     return false;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.TileBasedWorldModel
 * JD-Core Version:    0.7.0.1
 */