/*  1:   */ package sh.agent.aliens;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import org.newdawn.slick.Animation;
/*  6:   */ import org.newdawn.slick.Image;
/*  7:   */ import sh.agent.AgentModel;
/*  8:   */ import sh.agent.AgentView;
/*  9:   */ 
/* 10:   */ public class AlienView
/* 11:   */   extends AgentView
/* 12:   */ {
/* 13:   */   protected Animation placement;
/* 14:   */   
/* 15:   */   public AlienView(AgentModel agentModel, HashMap<String, ArrayList<Image>> all, Image selectImage)
/* 16:   */   {
/* 17:19 */     super(agentModel);
/* 18:   */     try
/* 19:   */     {
/* 20:23 */       this.playerSelected = selectImage;
/* 21:   */       
/* 22:25 */       initGraphics(all);
/* 23:   */     }
/* 24:   */     catch (Exception e)
/* 25:   */     {
/* 26:30 */       e.printStackTrace();
/* 27:   */     }
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.aliens.AlienView
 * JD-Core Version:    0.7.0.1
 */