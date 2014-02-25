/*  1:   */ package sh.agent.weapons.projectiles;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Animation;
/*  4:   */ import org.newdawn.slick.Graphics;
/*  5:   */ import org.newdawn.slick.Image;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import sh.agent.AgentModel;
/*  8:   */ import sh.agent.AgentView;
/*  9:   */ 
/* 10:   */ public class BulletView
/* 11:   */   extends AgentView
/* 12:   */ {
/* 13:   */   public BulletView(AgentModel model, Image playerImage)
/* 14:   */   {
/* 15:19 */     super(model);
/* 16:20 */     this.playerImage = playerImage;
/* 17:   */     
/* 18:22 */     initGraphics();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void draw(Graphics g)
/* 22:   */     throws SlickException
/* 23:   */   {
/* 24:29 */     this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.weapons.projectiles.BulletView
 * JD-Core Version:    0.7.0.1
 */