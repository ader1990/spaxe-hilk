/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import com.esotericsoftware.kryonet.Connection;
/*   4:    */ import com.esotericsoftware.kryonet.Listener;
/*   5:    */ import de.matthiasmann.twl.GUI;
/*   6:    */ import de.matthiasmann.twl.Label;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.Input;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.state.StateBasedGame;
/*  12:    */ import sh.SpaceHulkGameContainer;
/*  13:    */ import sh.gui.TWL.BasicTWLGameState;
/*  14:    */ import sh.gui.TWL.RootPane;
/*  15:    */ import sh.gui.widgets.ChatBox;
/*  16:    */ import sh.multiplayer.ChatMessage;
/*  17:    */ import sh.multiplayer.SyncLijst;
/*  18:    */ 
/*  19:    */ public class EndGameState
/*  20:    */   extends BasicTWLGameState
/*  21:    */ {
/*  22: 34 */   private int stateID = -1;
/*  23:    */   private Label playerLabel;
/*  24:    */   private Label factionLabel;
/*  25:    */   private Label pointsLabel;
/*  26:    */   private Label teamLabel;
/*  27:    */   private Label statusLabel;
/*  28: 41 */   private boolean showChat = true;
/*  29:    */   private Label[] names;
/*  30:    */   private Label[] races;
/*  31:    */   private Label[] points;
/*  32:    */   private Label[] teams;
/*  33:    */   private Label[] status;
/*  34:    */   private ChatBox chatbox;
/*  35:    */   SpaceHulkGameContainer container;
/*  36:    */   StateBasedGame sb;
/*  37:    */   SyncLijst overview;
/*  38:    */   
/*  39:    */   protected RootPane createRootPane()
/*  40:    */   {
/*  41: 56 */     RootPane rp = super.createRootPane();
/*  42: 57 */     rp.setSize(this.container.getScreenWidth(), this.container.getScreenHeight());
/*  43: 58 */     rp.setTheme("");
/*  44: 59 */     int players = this.container.getPlayers();
/*  45:    */     
/*  46: 61 */     this.playerLabel = new Label();
/*  47: 62 */     this.playerLabel.setText("Players:");
/*  48: 63 */     this.factionLabel = new Label();
/*  49: 64 */     this.factionLabel.setText("Faction:");
/*  50: 65 */     this.pointsLabel = new Label();
/*  51: 66 */     this.pointsLabel.setText("Points:");
/*  52: 67 */     this.teamLabel = new Label();
/*  53: 68 */     this.teamLabel.setText("Team:");
/*  54: 69 */     this.chatbox = new ChatBox(this.container);
/*  55:    */     
/*  56:    */ 
/*  57: 72 */     this.statusLabel = new Label();
/*  58: 73 */     this.statusLabel.setText("Status:");
/*  59:    */     
/*  60: 75 */     this.names = new Label[players];
/*  61: 76 */     this.races = new Label[players];
/*  62: 77 */     this.points = new Label[players];
/*  63: 78 */     this.teams = new Label[players];
/*  64: 79 */     this.status = new Label[players];
/*  65: 83 */     for (int i = 0; i < players; i++)
/*  66:    */     {
/*  67: 85 */       this.names[i] = new Label();
/*  68: 86 */       this.teams[i] = new Label();
/*  69: 87 */       this.points[i] = new Label();
/*  70: 88 */       this.races[i] = new Label();
/*  71: 89 */       this.status[i] = new Label();
/*  72:    */       
/*  73: 91 */       rp.add(this.names[i]);
/*  74: 92 */       rp.add(this.races[i]);
/*  75: 93 */       rp.add(this.teams[i]);
/*  76: 94 */       rp.add(this.points[i]);
/*  77: 95 */       rp.add(this.status[i]);
/*  78:    */     }
/*  79: 98 */     rp.add(this.chatbox);
/*  80: 99 */     rp.add(this.factionLabel);
/*  81:100 */     rp.add(this.playerLabel);
/*  82:101 */     rp.add(this.pointsLabel);
/*  83:102 */     rp.add(this.teamLabel);
/*  84:103 */     rp.add(this.statusLabel);
/*  85:    */     
/*  86:105 */     return rp;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void layoutRootPane()
/*  90:    */   {
/*  91:111 */     int middleX = this.container.getWidth() / 2;
/*  92:112 */     int middleY = this.container.getHeight() / 2;
/*  93:    */     
/*  94:114 */     int maxX = this.container.getWidth();
/*  95:115 */     int maxY = this.container.getHeight();
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:120 */     this.playerLabel.setPosition(50, 60);
/* 101:121 */     this.playerLabel.setSize(100, 20);
/* 102:122 */     for (int i = 0; i < this.names.length; i++)
/* 103:    */     {
/* 104:124 */       this.names[i].setPosition(50, 100 + 50 * i);
/* 105:125 */       this.names[i].setSize(100, 20);
/* 106:    */     }
/* 107:128 */     this.factionLabel.setPosition(175, 60);
/* 108:129 */     this.factionLabel.setSize(175, 20);
/* 109:130 */     for (int i = 0; i < this.status.length; i++)
/* 110:    */     {
/* 111:132 */       this.races[i].setPosition(350, 100 + 50 * i);
/* 112:133 */       this.races[i].setSize(100, 25);
/* 113:    */     }
/* 114:136 */     this.pointsLabel.setPosition(300, 60);
/* 115:137 */     this.pointsLabel.setSize(100, 20);
/* 116:138 */     for (int i = 0; i < this.points.length; i++)
/* 117:    */     {
/* 118:140 */       this.points[i].setPosition(300, 100 + 50 * i);
/* 119:141 */       this.points[i].setSize(100, 25);
/* 120:    */     }
/* 121:144 */     this.teamLabel.setPosition(425, 60);
/* 122:145 */     this.teamLabel.setSize(100, 20);
/* 123:146 */     for (int i = 0; i < this.teams.length; i++)
/* 124:    */     {
/* 125:148 */       this.teams[i].setPosition(425, 100 + 50 * i);
/* 126:149 */       this.teams[i].setSize(100, 25);
/* 127:    */     }
/* 128:152 */     this.statusLabel.setPosition(550, 60);
/* 129:153 */     this.statusLabel.setSize(100, 20);
/* 130:154 */     for (int i = 0; i < this.status.length; i++)
/* 131:    */     {
/* 132:156 */       this.status[i].setPosition(500, 100 + 50 * i);
/* 133:157 */       this.status[i].setSize(100, 25);
/* 134:    */     }
/* 135:160 */     this.chatbox.setPosition(maxX / 10, maxY - 100);
/* 136:161 */     this.chatbox.setSize(maxX - maxX / 5, 100);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public EndGameState(int stateID)
/* 140:    */   {
/* 141:166 */     this.stateID = stateID;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void enter(GameContainer cont, StateBasedGame game)
/* 145:    */     throws SlickException
/* 146:    */   {
/* 147:175 */     super.enter(this.container, game);
/* 148:176 */     this.overview = this.container.getOverview();
/* 149:177 */     for (int i = 0; i < this.container.getPlayers(); i++)
/* 150:    */     {
/* 151:179 */       this.names[i].setText(this.overview.namen[i]);
/* 152:180 */       this.teams[i].setText(String.valueOf(this.overview.teams[i]));
/* 153:181 */       this.points[i].setText(String.valueOf(this.overview.points[i]));
/* 154:    */       
/* 155:183 */       String statusText = "";
/* 156:184 */       this.overview.defeated[i];
/* 157:    */       
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:192 */       this.status[i].setText(statusText);
/* 165:    */     }
/* 166:195 */     if (this.container.isServer()) {
/* 167:197 */       this.container.AddServerListener(new JoinServerListener());
/* 168:    */     } else {
/* 169:201 */       this.container.AddClientListener(new JoinClientListener());
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getID()
/* 174:    */   {
/* 175:208 */     return this.stateID;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void init(GameContainer cont, StateBasedGame game)
/* 179:    */     throws SlickException
/* 180:    */   {
/* 181:215 */     this.container = ((SpaceHulkGameContainer)cont);
/* 182:216 */     this.sb = game;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
/* 186:    */     throws SlickException
/* 187:    */   {}
/* 188:    */   
/* 189:    */   public void update(GameContainer container, StateBasedGame arg1, int arg2)
/* 190:    */     throws SlickException
/* 191:    */   {
/* 192:236 */     if (container.getInput().isKeyPressed(15))
/* 193:    */     {
/* 194:238 */       this.showChat = (!this.showChat);
/* 195:239 */       this.chatbox.setVisible(this.showChat);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public synchronized void addRowThreadsafe(final ChatMessage msg)
/* 200:    */   {
/* 201:247 */     GUI gui = getRootPane().getGUI();
/* 202:248 */     if (gui != null) {
/* 203:250 */       gui.invokeLater(new Runnable()
/* 204:    */       {
/* 205:    */         public void run()
/* 206:    */         {
/* 207:254 */           EndGameState.this.chatbox.appendRow(msg.name, msg.text);
/* 208:    */         }
/* 209:    */       });
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public class JoinServerListener
/* 214:    */     extends Listener
/* 215:    */   {
/* 216:    */     public JoinServerListener() {}
/* 217:    */     
/* 218:    */     public void connected(Connection arg0)
/* 219:    */     {
/* 220:267 */       super.connected(arg0);
/* 221:    */     }
/* 222:    */     
/* 223:    */     public void received(Connection connection, Object object)
/* 224:    */     {
/* 225:273 */       if ((object instanceof ChatMessage))
/* 226:    */       {
/* 227:275 */         ChatMessage message = (ChatMessage)object;
/* 228:276 */         EndGameState.this.container.send(message, connection);
/* 229:277 */         EndGameState.this.addRowThreadsafe(message);
/* 230:    */       }
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public class JoinClientListener
/* 235:    */     extends Listener
/* 236:    */   {
/* 237:    */     public JoinClientListener() {}
/* 238:    */     
/* 239:    */     public void received(Connection connection, Object object)
/* 240:    */     {
/* 241:290 */       if ((object instanceof ChatMessage))
/* 242:    */       {
/* 243:292 */         ChatMessage message = (ChatMessage)object;
/* 244:    */         
/* 245:294 */         EndGameState.this.addRowThreadsafe(message);
/* 246:    */       }
/* 247:    */     }
/* 248:    */   }
/* 249:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.EndGameState
 * JD-Core Version:    0.7.0.1
 */