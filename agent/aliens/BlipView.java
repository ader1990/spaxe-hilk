/*  1:   */ package sh.agent.aliens;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.newdawn.slick.Animation;
/*  5:   */ import org.newdawn.slick.Graphics;
/*  6:   */ import org.newdawn.slick.Image;
/*  7:   */ import org.newdawn.slick.SlickException;
/*  8:   */ import sh.agent.AgentModel;
/*  9:   */ import sh.agent.AgentView;
/* 10:   */ import sh.world.shworld.SpaceHulkWorldModel;
/* 11:   */ 
/* 12:   */ public class BlipView
/* 13:   */   extends AgentView
/* 14:   */ {
/* 15:   */   protected Animation placement;
/* 16:   */   
/* 17:   */   public BlipView(BlipModel model, List<Image> playerImage, Image selectImage, Image alienPlacement)
/* 18:   */   {
/* 19:16 */     super(model);
/* 20:   */     try
/* 21:   */     {
/* 22:19 */       if (model.getColour() < playerImage.size()) {
/* 23:21 */         this.playerImage = ((Image)playerImage.get(model.getColour()));
/* 24:   */       } else {
/* 25:25 */         this.playerImage = ((Image)playerImage.get(0));
/* 26:   */       }
/* 27:27 */       this.playerSelected = selectImage;
/* 28:   */       
/* 29:29 */       this.placement = new Animation();
/* 30:30 */       this.placement.addFrame(alienPlacement, 1);
/* 31:31 */       initGraphics();
/* 32:   */     }
/* 33:   */     catch (Exception e)
/* 34:   */     {
/* 35:35 */       e.printStackTrace();
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void draw(Graphics g)
/* 40:   */     throws SlickException
/* 41:   */   {
/* 42:44 */     if (((BlipModel)this.model).converting)
/* 43:   */     {
/* 44:46 */       float x = this.model.getX();
/* 45:47 */       float y = this.model.getY();
/* 46:48 */       if (this.model.worldModel.tileWalkable(x, y)) {
/* 47:50 */         this.placement.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 48:   */       }
/* 49:   */     }
/* 50:54 */     if (this.model.isSelected()) {
/* 51:56 */       this.selected.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 52:   */     }
/* 53:59 */     this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.aliens.BlipView
 * JD-Core Version:    0.7.0.1
 */