/*  1:   */ package sh.agent.door;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Animation;
/*  4:   */ import org.newdawn.slick.Graphics;
/*  5:   */ import org.newdawn.slick.Image;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import sh.agent.AgentModel;
/*  8:   */ import sh.agent.AgentView;
/*  9:   */ 
/* 10:   */ public class DoorView
/* 11:   */   extends AgentView
/* 12:   */ {
/* 13:   */   public DoorView(DoorModel model, Image playerImage)
/* 14:   */   {
/* 15:14 */     super(model);
/* 16:   */     try
/* 17:   */     {
/* 18:18 */       this.playerImage = playerImage;
/* 19:19 */       this.playerSelected = null;
/* 20:   */       
/* 21:21 */       initGraphics();
/* 22:   */     }
/* 23:   */     catch (Exception localException) {}
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void draw(Graphics g)
/* 27:   */     throws SlickException
/* 28:   */   {
/* 29:32 */     g.rotate(0.0F, 0.0F, this.model.getAngle());
/* 30:34 */     if (!((DoorModel)this.model).isOpen()) {
/* 31:38 */       this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.door.DoorView
 * JD-Core Version:    0.7.0.1
 */