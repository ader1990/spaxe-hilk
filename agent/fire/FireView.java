/*  1:   */ package sh.agent.fire;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.newdawn.slick.Animation;
/*  5:   */ import org.newdawn.slick.Image;
/*  6:   */ import sh.agent.AgentModel;
/*  7:   */ import sh.agent.AgentView;
/*  8:   */ 
/*  9:   */ public class FireView
/* 10:   */   extends AgentView
/* 11:   */ {
/* 12:   */   List<Image> fireImage;
/* 13:   */   
/* 14:   */   public FireView(AgentModel model, List<Image> playerImage)
/* 15:   */   {
/* 16:16 */     super(model);
/* 17:   */     try
/* 18:   */     {
/* 19:19 */       this.fireImage = playerImage;
/* 20:20 */       this.playerSelected = null;
/* 21:   */       
/* 22:22 */       initGraphics();
/* 23:   */     }
/* 24:   */     catch (Exception localException) {}
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected void initGraphics()
/* 28:   */   {
/* 29:33 */     this.animation = new Animation();
/* 30:34 */     for (int i = 0; i < this.fireImage.size(); i++) {
/* 31:36 */       this.animation.addFrame((Image)this.fireImage.get(i), 100);
/* 32:   */     }
/* 33:38 */     this.selected = new Animation();
/* 34:39 */     if (this.playerSelected != null) {
/* 35:41 */       this.selected.addFrame(this.playerSelected, 1);
/* 36:   */     }
/* 37:43 */     this.height = (this.animation.getHeight() / this.model.transformX(1.0F));
/* 38:44 */     this.width = (this.animation.getWidth() / this.model.transformX(1.0F));
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.fire.FireView
 * JD-Core Version:    0.7.0.1
 */