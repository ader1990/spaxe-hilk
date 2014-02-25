/*  1:   */ package sh.agent.marines;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import org.newdawn.slick.Animation;
/*  6:   */ import org.newdawn.slick.Color;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import sh.agent.AgentModel;
/* 11:   */ import sh.agent.AgentView;
/* 12:   */ 
/* 13:   */ public class MarineView
/* 14:   */   extends AgentView
/* 15:   */ {
/* 16:   */   protected Animation overwatch;
/* 17:   */   
/* 18:   */   public MarineView(AgentModel agentModel, HashMap<String, ArrayList<Image>> all, Image selectImage, Image overWatch)
/* 19:   */   {
/* 20:22 */     super(agentModel);
/* 21:   */     try
/* 22:   */     {
/* 23:27 */       this.playerSelected = selectImage;
/* 24:   */       
/* 25:29 */       initGraphics(all);
/* 26:30 */       this.overwatch = new Animation();
/* 27:31 */       this.overwatch.addFrame(overWatch, 1);
/* 28:   */     }
/* 29:   */     catch (Exception e)
/* 30:   */     {
/* 31:35 */       e.printStackTrace();
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void draw(Graphics g)
/* 36:   */     throws SlickException
/* 37:   */   {
/* 38:43 */     if (((MarineModel)this.model).isOverwatch()) {
/* 39:45 */       this.overwatch.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 40:   */     }
/* 41:48 */     if (!this.model.isMoving()) {
/* 42:51 */       if (this.currentAnimation.equals("walking")) {
/* 43:53 */         setDefault("default");
/* 44:   */       }
/* 45:   */     }
/* 46:58 */     if (this.model.isAnimating()) {
/* 47:60 */       if (this.animation.isStopped())
/* 48:   */       {
/* 49:62 */         setDefault("default");
/* 50:63 */         this.model.setAnimating(false);
/* 51:   */       }
/* 52:   */     }
/* 53:66 */     g.rotate(0.0F, 0.0F, this.model.getAngle());
/* 54:67 */     if (this.model.isSelected()) {
/* 55:69 */       this.selected.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 56:   */     }
/* 57:73 */     this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 58:   */     
/* 59:75 */     g.rotate(0.0F, 0.0F, -this.model.getAngle());
/* 60:76 */     g.setColor(Color.white);
/* 61:77 */     g.scale(0.01F, 0.01F);
/* 62:78 */     String text = this.model.getFloatingText();
/* 63:79 */     if ((text != null) && (text != ""))
/* 64:   */     {
/* 65:82 */       this.textCount += 1;
/* 66:   */       
/* 67:84 */       g.drawString(text, 0.0F, -this.height - this.textCount * 0.1F);
/* 68:85 */       if (this.textCount == this.textFrames)
/* 69:   */       {
/* 70:87 */         this.textCount = 0;
/* 71:88 */         this.model.setFloatingText("");
/* 72:   */       }
/* 73:   */     }
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.marines.MarineView
 * JD-Core Version:    0.7.0.1
 */