/*   1:    */ package sh.gameobject;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Observable;
/*   5:    */ import java.util.Stack;
/*   6:    */ import org.newdawn.slick.geom.Vector2f;
/*   7:    */ import sh.multiplayer.AgentMovement;
/*   8:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*   9:    */ 
/*  10:    */ public class GameModel
/*  11:    */   extends Observable
/*  12:    */ {
/*  13:    */   protected int UUID;
/*  14:    */   protected int owner;
/*  15:    */   protected String name;
/*  16:    */   protected boolean selected;
/*  17: 26 */   protected Vector2f position = new Vector2f(0.0F, 0.0F);
/*  18:    */   protected float angle;
/*  19:    */   public SpaceHulkWorldModel worldModel;
/*  20:    */   protected int colour;
/*  21:    */   protected int maxAP;
/*  22:    */   protected boolean rotatable;
/*  23: 34 */   protected float angleDelta = 0.0F;
/*  24:    */   private String floatingText;
/*  25:    */   
/*  26:    */   public String getFloatingText()
/*  27:    */   {
/*  28: 40 */     return this.floatingText;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setFloatingText(String floatingText)
/*  32:    */   {
/*  33: 45 */     this.floatingText = floatingText;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getColour()
/*  37:    */   {
/*  38: 51 */     return this.colour;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setColour(int colour)
/*  42:    */   {
/*  43: 56 */     this.colour = colour;
/*  44:    */   }
/*  45:    */   
/*  46: 59 */   protected Stack<AgentMovement> movementStack = new Stack();
/*  47:    */   
/*  48:    */   public GameModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, boolean rotatable, int colour)
/*  49:    */   {
/*  50: 64 */     this.UUID = UUID;
/*  51: 65 */     this.name = name;
/*  52: 66 */     this.angle = angle;
/*  53: 67 */     this.position.set(x, y);
/*  54: 68 */     this.worldModel = worldModel;
/*  55:    */     
/*  56: 70 */     this.rotatable = rotatable;
/*  57:    */     
/*  58: 72 */     this.colour = colour;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isRotatable()
/*  62:    */   {
/*  63: 78 */     return this.rotatable;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setRotatable(boolean rotatable)
/*  67:    */   {
/*  68: 82 */     this.rotatable = rotatable;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getUUID()
/*  72:    */   {
/*  73: 86 */     return this.UUID;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getName()
/*  77:    */   {
/*  78: 91 */     return this.name;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Vector2f getPosition()
/*  82:    */   {
/*  83: 95 */     return this.position;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isSelected()
/*  87:    */   {
/*  88: 99 */     return this.selected;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public float getX()
/*  92:    */   {
/*  93:103 */     return this.position.x;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public float getY()
/*  97:    */   {
/*  98:107 */     return this.position.y;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setPosition(Vector2f newPosition)
/* 102:    */   {
/* 103:112 */     this.position.set(newPosition);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setAngle(float angle)
/* 107:    */   {
/* 108:116 */     this.angle = angle;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int alterAngle(float angle)
/* 112:    */   {
/* 113:120 */     this.angleDelta += 90.0F;
/* 114:121 */     this.angle = ((this.angle + angle) % 360.0F);
/* 115:122 */     if (this.angle < 0.0F) {
/* 116:125 */       this.angle = (360.0F + this.angle);
/* 117:    */     }
/* 118:127 */     System.out.println("New angle: " + this.angle);
/* 119:128 */     return 1;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public float getAngle()
/* 123:    */   {
/* 124:132 */     return this.angle;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void think() {}
/* 128:    */   
/* 129:    */   public void act() {}
/* 130:    */   
/* 131:    */   public void setSelected(boolean selected)
/* 132:    */   {
/* 133:144 */     this.selected = selected;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public float transformX(float x)
/* 137:    */   {
/* 138:148 */     return x * this.worldModel.getTileWidth();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public float transformY(float y)
/* 142:    */   {
/* 143:152 */     return y * this.worldModel.getTileHeight();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public float deTransformX(float x)
/* 147:    */   {
/* 148:156 */     return x / this.worldModel.getTileWidth();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public float deTransformY(float y)
/* 152:    */   {
/* 153:160 */     return y / this.worldModel.getTileHeight();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Vector2f localizePosition(Vector2f location)
/* 157:    */   {
/* 158:164 */     return location;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int tileReachable(float tx, float ty)
/* 162:    */   {
/* 163:168 */     return 0;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gameobject.GameModel
 * JD-Core Version:    0.7.0.1
 */