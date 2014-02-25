/*   1:    */ package sh.gameobject;
/*   2:    */ 
/*   3:    */ import java.util.Observable;
/*   4:    */ import java.util.Observer;
/*   5:    */ import org.newdawn.slick.Animation;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.UnicodeFont;
/*  11:    */ import org.newdawn.slick.geom.Vector2f;
/*  12:    */ 
/*  13:    */ public class GameView
/*  14:    */   implements Observer
/*  15:    */ {
/*  16:    */   protected GameModel model;
/*  17: 27 */   protected float width = 0.0F;
/*  18: 28 */   protected float height = 0.0F;
/*  19: 29 */   protected int textFrames = 100;
/*  20: 30 */   protected int textCount = 0;
/*  21:    */   protected Animation animation;
/*  22:    */   protected Animation selected;
/*  23:    */   protected Image playerImage;
/*  24:    */   protected Image playerSelected;
/*  25:    */   protected UnicodeFont font;
/*  26:    */   
/*  27:    */   public GameView(GameModel model)
/*  28:    */   {
/*  29: 39 */     this.model = model;
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33: 43 */     this.model.addObserver(this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void initGraphics()
/*  37:    */   {
/*  38: 48 */     this.animation = new Animation();
/*  39: 49 */     this.animation.addFrame(this.playerImage, 1);
/*  40: 50 */     this.selected = new Animation();
/*  41: 51 */     if (this.playerSelected != null) {
/*  42: 53 */       this.selected.addFrame(this.playerSelected, 1);
/*  43:    */     }
/*  44: 55 */     this.height = (this.animation.getHeight() / this.model.transformX(1.0F));
/*  45: 56 */     this.width = (this.animation.getWidth() / this.model.transformX(1.0F));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void draw(Graphics g)
/*  49:    */     throws SlickException
/*  50:    */   {
/*  51: 61 */     g.rotate(0.0F, 0.0F, this.model.getAngle());
/*  52: 62 */     this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/*  53: 63 */     if (this.model.isSelected()) {
/*  54: 65 */       this.selected.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/*  55:    */     }
/*  56: 68 */     g.rotate(0.0F, 0.0F, -this.model.getAngle());
/*  57: 69 */     g.setColor(Color.white);
/*  58: 70 */     g.scale(0.01F, 0.01F);
/*  59: 71 */     String text = this.model.getFloatingText();
/*  60: 72 */     if ((text != null) && (text != ""))
/*  61:    */     {
/*  62: 75 */       this.textCount += 1;
/*  63:    */       
/*  64: 77 */       g.drawString(text, 0.0F, -this.height - this.textCount * 0.1F);
/*  65: 78 */       if (this.textCount == this.textFrames)
/*  66:    */       {
/*  67: 80 */         this.textCount = 0;
/*  68: 81 */         this.model.setFloatingText("");
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void pushTransform(Graphics g)
/*  74:    */   {
/*  75: 87 */     g.pushTransform();
/*  76: 88 */     g.translate(this.model.getX(), this.model.getY());
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void popTransform(Graphics g)
/*  80:    */   {
/*  81: 93 */     g.popTransform();
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void drawCircle(float cx, float cy, float radius, Color color, boolean fill, Graphics g)
/*  85:    */   {
/*  86: 99 */     drawCircle(new Vector2f(cx, cy), radius, color, fill, g);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void drawCircle(Vector2f point, float radius, Color color, boolean fill, Graphics g)
/*  90:    */   {
/*  91:103 */     g.setColor(color);
/*  92:104 */     if (fill) {
/*  93:105 */       g.fillOval(point.x - radius, point.y - radius, radius * 2.0F, radius * 2.0F);
/*  94:    */     } else {
/*  95:107 */       g.drawOval(point.x - radius, point.y - radius, radius * 2.0F, radius * 2.0F);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected void drawRect(float cx, float cy, float width, float height, Color color, boolean fill, Graphics g)
/* 100:    */   {
/* 101:112 */     drawRect(new Vector2f(cx, cy), width, height, color, fill, g);
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void drawRect(Vector2f point, float width, float height, Color color, boolean fill, Graphics g)
/* 105:    */   {
/* 106:116 */     g.setColor(color);
/* 107:117 */     if (fill) {
/* 108:118 */       g.fillRect(point.x - width / 2.0F, point.y - height / 2.0F, width, height);
/* 109:    */     } else {
/* 110:120 */       g.drawOval(point.x - width / 2.0F, point.y - height / 2.0F, width, height);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected void drawLine(float x1, float y1, float x2, float y2, Color color, Graphics g)
/* 115:    */   {
/* 116:125 */     drawLine(new Vector2f(x1, y1), new Vector2f(x2, y2), color, g);
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void drawLine(Vector2f p0, Vector2f p1, Color color, Graphics g)
/* 120:    */   {
/* 121:129 */     g.setColor(color);
/* 122:130 */     g.drawLine(p0.x, p0.y, p1.x, p1.y);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void update(Observable o, Object arg) {}
/* 126:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gameobject.GameView
 * JD-Core Version:    0.7.0.1
 */