/*  1:   */ package sh.world;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import java.util.concurrent.ConcurrentSkipListMap;
/*  5:   */ import sh.agent.AgentController;
/*  6:   */ import sh.gameobject.GameController;
/*  7:   */ 
/*  8:   */ public class WorldController
/*  9:   */ {
/* 10:   */   protected WorldModel model;
/* 11:   */   protected WorldView view;
/* 12:24 */   private Map<Integer, AgentController> agentControllers = new ConcurrentSkipListMap();
/* 13:25 */   private Map<Integer, GameController> objectControllers = new ConcurrentSkipListMap();
/* 14:   */   
/* 15:   */   public WorldController(WorldModel model, WorldView view)
/* 16:   */   {
/* 17:28 */     this.model = model;
/* 18:29 */     this.view = view;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addAgentController(int UUID, AgentController agentController)
/* 22:   */   {
/* 23:33 */     this.agentControllers.put(Integer.valueOf(UUID), agentController);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void removeAgentController(int UUID)
/* 27:   */   {
/* 28:37 */     this.agentControllers.remove(Integer.valueOf(UUID));
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void clearAgentControllers()
/* 32:   */   {
/* 33:41 */     this.agentControllers.clear();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public AgentController getAgentController(int UUID)
/* 37:   */   {
/* 38:45 */     return (AgentController)this.agentControllers.get(Integer.valueOf(UUID));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void addGameController(int UUID, GameController agentController)
/* 42:   */   {
/* 43:49 */     this.objectControllers.put(Integer.valueOf(UUID), agentController);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void removeGameController(int UUID)
/* 47:   */   {
/* 48:53 */     this.objectControllers.remove(Integer.valueOf(UUID));
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void clearGameControllers()
/* 52:   */   {
/* 53:57 */     this.objectControllers.clear();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public GameController getGameController(int UUID)
/* 57:   */   {
/* 58:61 */     return (GameController)this.objectControllers.get(Integer.valueOf(UUID));
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void destroy()
/* 62:   */   {
/* 63:66 */     this.agentControllers.clear();
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.WorldController
 * JD-Core Version:    0.7.0.1
 */