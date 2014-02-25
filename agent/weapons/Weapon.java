/*   1:    */ package sh.agent.weapons;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Image;
/*   4:    */ import sh.agent.AgentModel;
/*   5:    */ import sh.utils.Diceroller;
/*   6:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*   7:    */ 
/*   8:    */ public class Weapon
/*   9:    */ {
/*  10:    */   private int ammo;
/*  11:    */   private int area;
/*  12:    */   private int attackbonus;
/*  13:    */   private boolean attackGround;
/*  14:    */   private Image bullet;
/*  15:    */   private long cooldown;
/*  16:    */   private boolean defaultWeapon;
/*  17:    */   private int fireAmount;
/*  18:    */   private int firecost;
/*  19:    */   private boolean flameWeapon;
/*  20:    */   private int hitdice;
/*  21:    */   private Image impact;
/*  22:    */   private int key;
/*  23:    */   private boolean melee;
/*  24:    */   private Image muzzle;
/*  25:    */   private String name;
/*  26:    */   private boolean overwatch;
/*  27:    */   private int range;
/*  28:    */   private int reloadcost;
/*  29:    */   private String sound;
/*  30:    */   private float spread;
/*  31:    */   private int toHit;
/*  32:    */   private String agentAnimation;
/*  33:    */   
/*  34:    */   public String getAgentAnimation()
/*  35:    */   {
/*  36: 37 */     return this.agentAnimation;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setAgentAnimation(String agentAnimation)
/*  40:    */   {
/*  41: 41 */     this.agentAnimation = agentAnimation;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Weapon(String name, int range, int hitdice, int area, int firecost, int ammo, int reloadcost, int toHit, int key, boolean melee, boolean flameWeapon, boolean defaultWeapon, boolean overwatch, int attackBonus, String sound, boolean attackGround, float spread, int fireAmount, long cooldown, Image bullet, Image muzzle, Image impact, String agentAnimation)
/*  45:    */   {
/*  46: 48 */     this.name = name;
/*  47: 49 */     this.range = range;
/*  48: 50 */     this.hitdice = hitdice;
/*  49: 51 */     this.area = area;
/*  50: 52 */     this.firecost = firecost;
/*  51: 53 */     this.ammo = ammo;
/*  52: 54 */     this.reloadcost = reloadcost;
/*  53: 55 */     this.toHit = toHit;
/*  54: 56 */     this.key = key;
/*  55: 57 */     this.melee = melee;
/*  56: 58 */     this.flameWeapon = flameWeapon;
/*  57: 59 */     this.defaultWeapon = defaultWeapon;
/*  58: 60 */     this.overwatch = overwatch;
/*  59: 61 */     this.sound = sound;
/*  60: 62 */     this.attackGround = attackGround;
/*  61: 63 */     this.spread = spread;
/*  62: 64 */     this.bullet = bullet;
/*  63: 65 */     this.impact = impact;
/*  64: 66 */     this.muzzle = muzzle;
/*  65: 67 */     this.fireAmount = fireAmount;
/*  66: 68 */     this.cooldown = cooldown;
/*  67: 69 */     this.agentAnimation = agentAnimation;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean attack(AgentModel attacker, AgentModel defender, SpaceHulkWorldModel world)
/*  71:    */   {
/*  72: 73 */     int attacks = attacker.getAttacks(defender.getUUID());
/*  73:    */     
/*  74: 75 */     int highest = 0;
/*  75: 76 */     for (int i = 0; i < this.hitdice; i++)
/*  76:    */     {
/*  77: 78 */       int roll = Diceroller.roll(this.toHit);
/*  78: 79 */       if (roll > highest) {
/*  79: 81 */         highest = roll;
/*  80:    */       }
/*  81:    */     }
/*  82: 84 */     attacker.setAttacks(defender.getUUID(), attacks + 1);
/*  83: 85 */     if (attacks + highest + this.attackbonus >= this.toHit) {
/*  84: 87 */       return true;
/*  85:    */     }
/*  86: 89 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getAmmo()
/*  90:    */   {
/*  91: 94 */     return this.ammo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getArea()
/*  95:    */   {
/*  96: 97 */     return this.area;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getAttackbonus()
/* 100:    */   {
/* 101:101 */     return this.attackbonus;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Image getBullet()
/* 105:    */   {
/* 106:105 */     return this.bullet;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public long getCooldown()
/* 110:    */   {
/* 111:109 */     return this.cooldown;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getFireAmount()
/* 115:    */   {
/* 116:113 */     return this.fireAmount;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getFirecost()
/* 120:    */   {
/* 121:116 */     return this.firecost;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getHitdice()
/* 125:    */   {
/* 126:119 */     return this.hitdice;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Image getImpact()
/* 130:    */   {
/* 131:123 */     return this.impact;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getKey()
/* 135:    */   {
/* 136:126 */     return this.key;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Image getMuzzle()
/* 140:    */   {
/* 141:130 */     return this.muzzle;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getName()
/* 145:    */   {
/* 146:134 */     return this.name;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int getRange()
/* 150:    */   {
/* 151:137 */     return this.range;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getReloadcost()
/* 155:    */   {
/* 156:140 */     return this.reloadcost;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getSound()
/* 160:    */   {
/* 161:144 */     return this.sound;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public float getSpread()
/* 165:    */   {
/* 166:148 */     return this.spread;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public int getToHit()
/* 170:    */   {
/* 171:151 */     return this.toHit;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean isAttackGround()
/* 175:    */   {
/* 176:155 */     return this.attackGround;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean isDefaultWeapon()
/* 180:    */   {
/* 181:158 */     return this.defaultWeapon;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean isFlameWeapon()
/* 185:    */   {
/* 186:161 */     return this.flameWeapon;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isMelee()
/* 190:    */   {
/* 191:164 */     return this.melee;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean isOverwatch()
/* 195:    */   {
/* 196:168 */     return this.overwatch;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void reload() {}
/* 200:    */   
/* 201:    */   public void setAmmo(int ammo)
/* 202:    */   {
/* 203:177 */     this.ammo = ammo;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setArea(int area)
/* 207:    */   {
/* 208:181 */     this.area = area;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setAttackbonus(int attackbonus)
/* 212:    */   {
/* 213:186 */     this.attackbonus = attackbonus;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setAttackGround(boolean attackGround)
/* 217:    */   {
/* 218:191 */     this.attackGround = attackGround;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setBullet(Image bullet)
/* 222:    */   {
/* 223:196 */     this.bullet = bullet;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setCooldown(long cooldown)
/* 227:    */   {
/* 228:201 */     this.cooldown = cooldown;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setDefaultWeapon(boolean defaultWeapon)
/* 232:    */   {
/* 233:205 */     this.defaultWeapon = defaultWeapon;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setFireAmount(int fireAmount)
/* 237:    */   {
/* 238:210 */     this.fireAmount = fireAmount;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setFirecost(int firecost)
/* 242:    */   {
/* 243:214 */     this.firecost = firecost;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setFlameWeapon(boolean flameWeapon)
/* 247:    */   {
/* 248:218 */     this.flameWeapon = flameWeapon;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setHitdice(int hitdice)
/* 252:    */   {
/* 253:222 */     this.hitdice = hitdice;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setImpact(Image impact)
/* 257:    */   {
/* 258:227 */     this.impact = impact;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setKey(int key)
/* 262:    */   {
/* 263:231 */     this.key = key;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setMelee(boolean melee)
/* 267:    */   {
/* 268:235 */     this.melee = melee;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setMuzzle(Image muzzle)
/* 272:    */   {
/* 273:240 */     this.muzzle = muzzle;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setName(String name)
/* 277:    */   {
/* 278:245 */     this.name = name;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setOverwatch(boolean overwatch)
/* 282:    */   {
/* 283:250 */     this.overwatch = overwatch;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setRange(int range)
/* 287:    */   {
/* 288:254 */     this.range = range;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setReloadcost(int reloadcost)
/* 292:    */   {
/* 293:258 */     this.reloadcost = reloadcost;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setSound(String sound)
/* 297:    */   {
/* 298:263 */     this.sound = sound;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setSpread(float spread)
/* 302:    */   {
/* 303:268 */     this.spread = spread;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setToHit(int toHit)
/* 307:    */   {
/* 308:272 */     this.toHit = toHit;
/* 309:    */   }
/* 310:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.weapons.Weapon
 * JD-Core Version:    0.7.0.1
 */