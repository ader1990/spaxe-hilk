/*   1:    */ package sh.world;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.Observable;
/*   5:    */ import java.util.Observer;
/*   6:    */ import java.util.concurrent.ConcurrentSkipListMap;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ import sh.agent.AgentView;
/*  10:    */ import sh.agent.weapons.projectiles.BulletView;
/*  11:    */ import sh.gameobject.GameView;
/*  12:    */ 
/*  13:    */ public class WorldView
/*  14:    */   implements Observer
/*  15:    */ {
/*  16:    */   protected WorldModel model;
/*  17: 29 */   private Map<Integer, AgentView> agentViews = new ConcurrentSkipListMap();
/*  18: 30 */   private Map<Integer, GameView> objectViews = new ConcurrentSkipListMap();
/*  19: 31 */   private Map<Integer, BulletView> bulletViews = new ConcurrentSkipListMap();
/*  20:    */   
/*  21:    */   public WorldView(WorldModel model)
/*  22:    */   {
/*  23: 33 */     this.model = model;
/*  24:    */     
/*  25: 35 */     this.model.addObserver(this);
/*  26:    */   }
/*  27:    */   
/*  28:    */   private void init() {}
/*  29:    */   
/*  30:    */   public void addAgentView(int UUID, AgentView agentView)
/*  31:    */   {
/*  32: 55 */     this.agentViews.put(Integer.valueOf(UUID), agentView);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void addGameView(int UUID, GameView agentView)
/*  36:    */   {
/*  37: 59 */     this.objectViews.put(Integer.valueOf(UUID), agentView);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void addBulletView(int UUID, BulletView agentView)
/*  41:    */   {
/*  42: 63 */     this.bulletViews.put(Integer.valueOf(UUID), agentView);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void removeAgentView(int UUID)
/*  46:    */   {
/*  47: 68 */     this.agentViews.remove(Integer.valueOf(UUID));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void removeBulletView(int UUID)
/*  51:    */   {
/*  52: 72 */     this.bulletViews.remove(Integer.valueOf(UUID));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void clearAgentViews()
/*  56:    */   {
/*  57: 76 */     this.agentViews.clear();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void clearGameViews()
/*  61:    */   {
/*  62: 79 */     this.objectViews.clear();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public AgentView getAgentView(int UUID)
/*  66:    */   {
/*  67: 83 */     return (AgentView)this.agentViews.get(Integer.valueOf(UUID));
/*  68:    */   }
/*  69:    */   
/*  70:    */   public GameView getGameView(int UUID)
/*  71:    */   {
/*  72: 87 */     return (GameView)this.objectViews.get(Integer.valueOf(UUID));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void destroy()
/*  76:    */   {
/*  77: 91 */     clearAgentViews();
/*  78: 92 */     clearGameViews();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void draw(Graphics graphics)
/*  82:    */     throws SlickException
/*  83:    */   {
/*  84: 98 */     for (GameView agent : this.objectViews.values())
/*  85:    */     {
/*  86: 99 */       agent.pushTransform(graphics);
/*  87:100 */       agent.draw(graphics);
/*  88:101 */       agent.popTransform(graphics);
/*  89:    */     }
/*  90:104 */     for (AgentView agent : this.agentViews.values())
/*  91:    */     {
/*  92:105 */       agent.pushTransform(graphics);
/*  93:106 */       agent.draw(graphics);
/*  94:107 */       agent.popTransform(graphics);
/*  95:    */     }
/*  96:110 */     for (BulletView agent : this.bulletViews.values())
/*  97:    */     {
/*  98:111 */       agent.pushTransform(graphics);
/*  99:112 */       agent.draw(graphics);
/* 100:113 */       agent.popTransform(graphics);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void update(Observable o, Object arg) {}
/* 105:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.WorldView
 * JD-Core Version:    0.7.0.1
 */