/*   1:    */ package sh.agent;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Observable;
/*   7:    */ import java.util.Observer;
/*   8:    */ import org.newdawn.slick.Animation;
/*   9:    */ import org.newdawn.slick.Color;
/*  10:    */ import org.newdawn.slick.Graphics;
/*  11:    */ import org.newdawn.slick.Image;
/*  12:    */ import org.newdawn.slick.SlickException;
/*  13:    */ import org.newdawn.slick.UnicodeFont;
/*  14:    */ import org.newdawn.slick.geom.Vector2f;
/*  15:    */ 
/*  16:    */ public class AgentView
/*  17:    */   implements Observer
/*  18:    */ {
/*  19:    */   protected AgentModel model;
/*  20: 22 */   protected HashMap<String, Animation> library = new HashMap();
/*  21: 24 */   protected float width = 0.0F;
/*  22: 25 */   protected float height = 0.0F;
/*  23: 26 */   protected int textFrames = 100;
/*  24: 27 */   protected int textCount = 0;
/*  25:    */   protected Animation animation;
/*  26:    */   protected Animation selected;
/*  27:    */   protected Image playerImage;
/*  28:    */   protected Image playerSelected;
/*  29:    */   protected UnicodeFont font;
/*  30:    */   protected String currentAnimation;
/*  31:    */   
/*  32:    */   public AgentView(AgentModel model)
/*  33:    */   {
/*  34: 37 */     this.model = model;
/*  35: 38 */     this.model.addObserver(this);
/*  36: 39 */     this.currentAnimation = "default";
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void initGraphics()
/*  40:    */   {
/*  41: 44 */     int colour = this.model.getColour();
/*  42: 46 */     if ((colour > 5) || (colour < 0)) {
/*  43: 48 */       colour = 0;
/*  44:    */     }
/*  45: 51 */     this.animation = new Animation();
/*  46: 52 */     this.animation.addFrame(this.playerImage, 1);
/*  47:    */     
/*  48: 54 */     this.library.put("default", this.animation);
/*  49:    */     
/*  50: 56 */     this.selected = new Animation();
/*  51: 57 */     if (this.playerSelected != null) {
/*  52: 59 */       this.selected.addFrame(this.playerSelected, 1);
/*  53:    */     }
/*  54: 61 */     this.height = (this.animation.getHeight() / this.model.transformX(1.0F));
/*  55: 62 */     this.width = (this.animation.getWidth() / this.model.transformX(1.0F));
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void initGraphics(HashMap<String, ArrayList<Image>> all)
/*  59:    */   {
/*  60: 67 */     int colour = this.model.getColour();
/*  61: 68 */     if (colour > 5) {
/*  62: 70 */       colour = 0;
/*  63:    */     }
/*  64: 72 */     ArrayList<Image> def = (ArrayList)all.get("default");
/*  65:    */     
/*  66: 74 */     this.animation = new Animation();
/*  67: 75 */     this.animation.addFrame((Image)def.get(colour), 1);
/*  68: 76 */     this.library.put("default", this.animation);
/*  69: 79 */     if (all.containsKey("walking"))
/*  70:    */     {
/*  71: 81 */       ArrayList<Image> walk = (ArrayList)all.get("walking");
/*  72: 82 */       Animation animation = new Animation();
/*  73: 83 */       Image walkImg = (Image)walk.get(colour);
/*  74: 84 */       int frames = walkImg.getWidth() / 128;
/*  75: 85 */       for (int i = 0; i < frames; i++) {
/*  76: 88 */         animation.addFrame(walkImg.getSubImage(i * 128, 0, 128, 128), 150);
/*  77:    */       }
/*  78: 90 */       this.library.put("walking", animation);
/*  79:    */     }
/*  80: 93 */     if (all.containsKey("melee"))
/*  81:    */     {
/*  82: 95 */       ArrayList<Image> melee = (ArrayList)all.get("melee");
/*  83: 96 */       Animation animation = new Animation();
/*  84: 97 */       Image walkImg = (Image)melee.get(colour);
/*  85: 98 */       int frames = walkImg.getWidth() / 128;
/*  86: 99 */       for (int i = 0; i < frames; i++) {
/*  87:102 */         animation.addFrame(walkImg.getSubImage(i * 128, 0, 128, 128), 75);
/*  88:    */       }
/*  89:104 */       this.library.put("melee", animation);
/*  90:    */     }
/*  91:107 */     this.selected = new Animation();
/*  92:108 */     if (this.playerSelected != null) {
/*  93:110 */       this.selected.addFrame(this.playerSelected, 1);
/*  94:    */     }
/*  95:112 */     this.height = (this.animation.getHeight() / this.model.transformX(1.0F));
/*  96:113 */     this.width = (this.animation.getWidth() / this.model.transformX(1.0F));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void addLibrary(String name, Animation animation)
/* 100:    */   {
/* 101:118 */     this.library.put(name, animation);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void addStaticLibrary(String name, Image animation)
/* 105:    */   {
/* 106:123 */     Animation ani = new Animation();
/* 107:124 */     ani.addFrame(animation, 1);
/* 108:125 */     this.library.put(name, ani);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDefault(String name)
/* 112:    */   {
/* 113:130 */     if (this.library.containsKey(name))
/* 114:    */     {
/* 115:132 */       this.currentAnimation = name;
/* 116:133 */       this.animation = ((Animation)this.library.get(name));
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setAnimation(String name, boolean loop)
/* 121:    */   {
/* 122:139 */     if (this.library.containsKey(name))
/* 123:    */     {
/* 124:141 */       System.out.println("animation " + name);
/* 125:142 */       this.currentAnimation = name;
/* 126:143 */       this.animation = ((Animation)this.library.get(name));
/* 127:144 */       this.animation.restart();
/* 128:145 */       this.animation.setLooping(loop);
/* 129:146 */       this.model.setAnimating(true);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void draw(Graphics g)
/* 134:    */     throws SlickException
/* 135:    */   {
/* 136:152 */     g.rotate(0.0F, 0.0F, this.model.getAngle());
/* 137:154 */     if (this.model.isSelected()) {
/* 138:156 */       this.selected.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 139:    */     }
/* 140:159 */     if (!this.model.isMoving()) {
/* 141:161 */       if (this.currentAnimation.equals("walking")) {
/* 142:163 */         setDefault("default");
/* 143:    */       }
/* 144:    */     }
/* 145:168 */     if (this.model.isAnimating()) {
/* 146:170 */       if (this.animation.isStopped())
/* 147:    */       {
/* 148:172 */         setDefault("default");
/* 149:173 */         this.model.setAnimating(false);
/* 150:    */       }
/* 151:    */     }
/* 152:176 */     this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 153:    */     
/* 154:178 */     g.rotate(0.0F, 0.0F, -this.model.getAngle());
/* 155:179 */     g.setColor(Color.white);
/* 156:180 */     g.scale(0.01F, 0.01F);
/* 157:181 */     String text = this.model.getFloatingText();
/* 158:182 */     if ((text != null) && (text != ""))
/* 159:    */     {
/* 160:185 */       this.textCount += 1;
/* 161:    */       
/* 162:187 */       g.drawString(text, 0.0F, -this.height - this.textCount * 0.1F);
/* 163:188 */       if (this.textCount == this.textFrames)
/* 164:    */       {
/* 165:190 */         this.textCount = 0;
/* 166:191 */         this.model.setFloatingText("");
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void pushTransform(Graphics g)
/* 172:    */   {
/* 173:197 */     g.pushTransform();
/* 174:198 */     g.translate(this.model.getX(), this.model.getY());
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void popTransform(Graphics g)
/* 178:    */   {
/* 179:203 */     g.popTransform();
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected void drawCircle(float cx, float cy, float radius, Color color, boolean fill, Graphics g)
/* 183:    */   {
/* 184:208 */     drawCircle(new Vector2f(cx, cy), radius, color, fill, g);
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected void drawCircle(Vector2f point, float radius, Color color, boolean fill, Graphics g)
/* 188:    */   {
/* 189:212 */     g.setColor(color);
/* 190:213 */     if (fill) {
/* 191:214 */       g.fillOval(point.x - radius, point.y - radius, radius * 2.0F, radius * 2.0F);
/* 192:    */     } else {
/* 193:216 */       g.drawOval(point.x - radius, point.y - radius, radius * 2.0F, radius * 2.0F);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected void drawRect(float cx, float cy, float width, float height, Color color, boolean fill, Graphics g)
/* 198:    */   {
/* 199:221 */     drawRect(new Vector2f(cx, cy), width, height, color, fill, g);
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected void drawRect(Vector2f point, float width, float height, Color color, boolean fill, Graphics g)
/* 203:    */   {
/* 204:225 */     g.setColor(color);
/* 205:226 */     if (fill) {
/* 206:227 */       g.fillRect(point.x - width / 2.0F, point.y - height / 2.0F, width, height);
/* 207:    */     } else {
/* 208:229 */       g.drawOval(point.x - width / 2.0F, point.y - height / 2.0F, width, height);
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   protected void drawLine(float x1, float y1, float x2, float y2, Color color, Graphics g)
/* 213:    */   {
/* 214:234 */     drawLine(new Vector2f(x1, y1), new Vector2f(x2, y2), color, g);
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected void drawLine(Vector2f p0, Vector2f p1, Color color, Graphics g)
/* 218:    */   {
/* 219:238 */     g.setColor(color);
/* 220:239 */     g.drawLine(p0.x, p0.y, p1.x, p1.y);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void update(Observable o, Object arg) {}
/* 224:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.AgentView
 * JD-Core Version:    0.7.0.1
 */