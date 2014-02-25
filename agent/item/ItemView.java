/*  1:   */ package sh.agent.item;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.Animation;
/*  4:   */ import org.newdawn.slick.Graphics;
/*  5:   */ import org.newdawn.slick.SlickException;
/*  6:   */ import sh.agent.AgentModel;
/*  7:   */ import sh.agent.AgentView;
/*  8:   */ 
/*  9:   */ public class ItemView
/* 10:   */   extends AgentView
/* 11:   */ {
/* 12:   */   public ItemView(ItemModel model, Animation animation, Animation selected)
/* 13:   */   {
/* 14:13 */     super(model);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void draw(Graphics g)
/* 18:   */     throws SlickException
/* 19:   */   {
/* 20:19 */     g.rotate(0.0F, 0.0F, this.model.getAngle());
/* 21:21 */     if (!((ItemModel)this.model).isOpen()) {
/* 22:23 */       this.animation.draw(-this.width / 2.0F, -this.height / 2.0F, this.width, this.height);
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.item.ItemView
 * JD-Core Version:    0.7.0.1
 */