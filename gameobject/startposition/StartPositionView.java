/*  1:   */ package sh.gameobject.startposition;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.newdawn.slick.Animation;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.Image;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ import sh.gameobject.GameModel;
/* 10:   */ import sh.gameobject.GameView;
/* 11:   */ 
/* 12:   */ public class StartPositionView
/* 13:   */   extends GameView
/* 14:   */ {
/* 15:   */   public StartPositionView(GameModel model, List<Image> playerImage)
/* 16:   */   {
/* 17:19 */     super(model);
/* 18:   */     try
/* 19:   */     {
/* 20:22 */       if (model.getColour() < playerImage.size()) {
/* 21:24 */         this.playerImage = ((Image)playerImage.get(model.getColour()));
/* 22:   */       } else {
/* 23:28 */         this.playerImage = ((Image)playerImage.get(0));
/* 24:   */       }
/* 25:31 */       initGraphics();
/* 26:   */     }
/* 27:   */     catch (Exception e)
/* 28:   */     {
/* 29:36 */       e.printStackTrace();
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void draw(Graphics g)
/* 34:   */     throws SlickException
/* 35:   */   {
/* 36:43 */     if (((StartPositionModel)this.model).isOpen())
/* 37:   */     {
/* 38:45 */       g.rotate(0.0F, 0.0F, this.model.getAngle());
/* 39:46 */       this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 40:47 */       if (this.model.isSelected()) {
/* 41:49 */         this.selected.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 42:   */       }
/* 43:52 */       g.rotate(0.0F, 0.0F, -this.model.getAngle());
/* 44:53 */       g.setColor(Color.white);
/* 45:54 */       g.scale(0.01F, 0.01F);
/* 46:55 */       String text = this.model.getFloatingText();
/* 47:56 */       if ((text != null) && (text != ""))
/* 48:   */       {
/* 49:59 */         this.textCount += 1;
/* 50:   */         
/* 51:61 */         g.drawString(text, 0.0F, -this.height - this.textCount * 0.1F);
/* 52:62 */         if (this.textCount == this.textFrames)
/* 53:   */         {
/* 54:64 */           this.textCount = 0;
/* 55:65 */           this.model.setFloatingText("");
/* 56:   */         }
/* 57:   */       }
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gameobject.startposition.StartPositionView
 * JD-Core Version:    0.7.0.1
 */