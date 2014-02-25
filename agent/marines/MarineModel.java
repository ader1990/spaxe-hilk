/*   1:    */ package sh.agent.marines;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import sh.agent.movement.PathAgentModel;
/*   5:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*   6:    */ 
/*   7:    */ public class MarineModel
/*   8:    */   extends PathAgentModel
/*   9:    */ {
/*  10: 12 */   private boolean overwatch = false;
/*  11:    */   private int CPBonus;
/*  12:    */   
/*  13:    */   public boolean isOverwatch()
/*  14:    */   {
/*  15: 16 */     return this.overwatch;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void setOverwatch(boolean overwatch, int weapon)
/*  19:    */   {
/*  20: 19 */     this.overwatch = overwatch;
/*  21: 20 */     setCurrentWeapon(weapon);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setOverwatch(boolean overwatch)
/*  25:    */   {
/*  26: 24 */     this.overwatch = overwatch;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public MarineModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour, int CpBonus)
/*  30:    */   {
/*  31: 28 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/*  32:    */     
/*  33: 30 */     this.CPBonus = CpBonus;
/*  34:    */     
/*  35: 32 */     this.mass = 0.1F;
/*  36: 33 */     this.maxSpeed = 0.02F;
/*  37: 34 */     this.maxForce = 0.005F;
/*  38: 35 */     this.damping = 0.4F;
/*  39: 36 */     this.breaking = 0.9F;
/*  40:    */     
/*  41: 38 */     this.agentRadius = 0.2F;
/*  42: 39 */     this.obstacleRadius = 0.1F;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int rotatingCost(float turned)
/*  46:    */   {
/*  47: 43 */     if (turned < 0.0F) {
/*  48: 45 */       turned = -turned;
/*  49:    */     }
/*  50: 47 */     switch ((int)turned)
/*  51:    */     {
/*  52:    */     case 0: 
/*  53: 50 */       return 0;
/*  54:    */     case 90: 
/*  55: 53 */       return 1;
/*  56:    */     case 180: 
/*  57: 56 */       return 2;
/*  58:    */     case 270: 
/*  59: 59 */       return 1;
/*  60:    */     }
/*  61: 62 */     return 0;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean fov(float x, float y)
/*  65:    */   {
/*  66: 69 */     switch ((int)getAngle())
/*  67:    */     {
/*  68:    */     case 0: 
/*  69: 72 */       if (x > getX() - 0.5F) {
/*  70: 74 */         return true;
/*  71:    */       }
/*  72:    */       break;
/*  73:    */     case 90: 
/*  74: 80 */       if (y > getY() - 0.5F) {
/*  75: 82 */         return true;
/*  76:    */       }
/*  77:    */       break;
/*  78:    */     case 180: 
/*  79: 88 */       if (x < getX() - 0.5F) {
/*  80: 90 */         return true;
/*  81:    */       }
/*  82:    */       break;
/*  83:    */     case 270: 
/*  84: 95 */       if (y < getY() - 0.5F) {
/*  85: 97 */         return true;
/*  86:    */       }
/*  87:    */       break;
/*  88:    */     }
/*  89:102 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int tileReachable(float tx, float ty)
/*  93:    */   {
/*  94:109 */     int x = Math.round(getX() - 0.5F);
/*  95:110 */     int y = Math.round(getY() - 0.5F);
/*  96:    */     
/*  97:112 */     System.out.println("new x" + tx + " y " + ty + " old x " + x + " y " + y);
/*  98:    */     
/*  99:114 */     System.out.println("reachable?" + (int)getAngle());
/* 100:116 */     switch ((int)getAngle())
/* 101:    */     {
/* 102:    */     case 0: 
/* 103:119 */       if ((x + 1 == tx) && (ty >= y - 1) && (ty <= y + 1)) {
/* 104:121 */         return 1;
/* 105:    */       }
/* 106:123 */       if ((x - 1 == tx) && (ty == y)) {
/* 107:125 */         return 2;
/* 108:    */       }
/* 109:    */       break;
/* 110:    */     case 90: 
/* 111:131 */       if ((y + 1 == ty) && (tx >= x - 1) && (tx <= x + 1)) {
/* 112:133 */         return 1;
/* 113:    */       }
/* 114:135 */       if ((y - 1 == ty) && (tx == x)) {
/* 115:137 */         return 2;
/* 116:    */       }
/* 117:    */       break;
/* 118:    */     case 180: 
/* 119:141 */       if ((x - 1 == tx) && (ty >= y - 1) && (ty <= y + 1)) {
/* 120:144 */         return 1;
/* 121:    */       }
/* 122:146 */       if ((x + 1 == tx) && (ty == y)) {
/* 123:148 */         return 2;
/* 124:    */       }
/* 125:    */       break;
/* 126:    */     case 270: 
/* 127:152 */       if ((y - 1 == ty) && (tx >= x - 1) && (tx <= x + 1)) {
/* 128:154 */         return 1;
/* 129:    */       }
/* 130:156 */       if ((y + 1 == ty) && (tx == x)) {
/* 131:158 */         return 2;
/* 132:    */       }
/* 133:    */       break;
/* 134:    */     }
/* 135:163 */     return 0;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getCpBonus()
/* 139:    */   {
/* 140:168 */     return this.CPBonus;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.marines.MarineModel
 * JD-Core Version:    0.7.0.1
 */