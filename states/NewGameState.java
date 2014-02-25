/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import com.esotericsoftware.kryonet.Client;
/*   4:    */ import com.esotericsoftware.kryonet.Connection;
/*   5:    */ import com.esotericsoftware.kryonet.Listener;
/*   6:    */ import de.matthiasmann.twl.Button;
/*   7:    */ import de.matthiasmann.twl.CallbackWithReason;
/*   8:    */ import de.matthiasmann.twl.EditField;
/*   9:    */ import de.matthiasmann.twl.Label;
/*  10:    */ import de.matthiasmann.twl.ListBox;
/*  11:    */ import de.matthiasmann.twl.ListBox.CallbackReason;
/*  12:    */ import de.matthiasmann.twl.model.SimpleChangableListModel;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.net.InetAddress;
/*  15:    */ import java.util.List;
/*  16:    */ import org.newdawn.slick.GameContainer;
/*  17:    */ import org.newdawn.slick.Graphics;
/*  18:    */ import org.newdawn.slick.Image;
/*  19:    */ import org.newdawn.slick.SlickException;
/*  20:    */ import org.newdawn.slick.state.StateBasedGame;
/*  21:    */ import sh.SpaceHulkGameContainer;
/*  22:    */ import sh.gui.TWL.BasicTWLGameState;
/*  23:    */ import sh.gui.TWL.RootPane;
/*  24:    */ import sh.multiplayer.MapName;
/*  25:    */ import sh.multiplayer.PlayerJoined;
/*  26:    */ 
/*  27:    */ public class NewGameState
/*  28:    */   extends BasicTWLGameState
/*  29:    */ {
/*  30:    */   private Button joinGame;
/*  31:    */   private Button back;
/*  32:    */   private Button hostGame;
/*  33:    */   private Button refresh;
/*  34:    */   private Label ipAddressLabel;
/*  35:    */   private Label searching;
/*  36:    */   private EditField address;
/*  37:    */   private EditField userName;
/*  38:    */   private ListBox<String> ips;
/*  39: 53 */   private boolean hasMap = false;
/*  40: 54 */   private boolean scanForHosts = true;
/*  41:    */   private List<InetAddress> addresses;
/*  42:    */   private JoinCLientListener clientListener;
/*  43:    */   Image background;
/*  44:    */   SpaceHulkGameContainer container;
/*  45:    */   StateBasedGame sb;
/*  46:    */   String text;
/*  47: 62 */   private int stateID = -1;
/*  48:    */   
/*  49:    */   public NewGameState(int stateID)
/*  50:    */   {
/*  51: 66 */     this.stateID = stateID;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getID()
/*  55:    */   {
/*  56: 72 */     return this.stateID;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void init(GameContainer cont, StateBasedGame sb)
/*  60:    */     throws SlickException
/*  61:    */   {
/*  62: 80 */     this.container = ((SpaceHulkGameContainer)cont);
/*  63: 81 */     this.sb = sb;
/*  64: 82 */     this.container.setupClient();
/*  65: 83 */     this.clientListener = new JoinCLientListener();
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected RootPane createRootPane()
/*  69:    */   {
/*  70: 90 */     RootPane rp = super.createRootPane();
/*  71: 91 */     rp.setSize(this.container.getScreenWidth(), this.container.getScreenHeight());
/*  72: 92 */     rp.setTheme("");
/*  73: 93 */     this.ipAddressLabel = new Label();
/*  74: 94 */     this.ipAddressLabel.setText("IP address:");
/*  75: 95 */     this.searching = new Label();
/*  76:    */     
/*  77: 97 */     this.address = new EditField();
/*  78: 98 */     this.address.setTheme("editfield");
/*  79:    */     
/*  80:100 */     this.userName = new EditField();
/*  81:101 */     this.userName.setTheme("editfield");
/*  82:102 */     this.userName.setText(this.container.getName());
/*  83:    */     
/*  84:104 */     this.back = new Button("Back");
/*  85:105 */     this.back.addCallback(new Runnable()
/*  86:    */     {
/*  87:    */       public void run()
/*  88:    */       {
/*  89:109 */         NewGameState.this.container.playSound("click");
/*  90:110 */         NewGameState.this.sb.enterState(0);
/*  91:    */       }
/*  92:114 */     });
/*  93:115 */     this.refresh = new Button();
/*  94:116 */     this.refresh.setText("Refresh list");
/*  95:117 */     this.refresh.addCallback(new Runnable()
/*  96:    */     {
/*  97:    */       public void run()
/*  98:    */       {
/*  99:121 */         NewGameState.this.container.playSound("click");
/* 100:122 */         NewGameState.this.scanForHosts = true;
/* 101:    */       }
/* 102:127 */     });
/* 103:128 */     this.address.setText(this.container.getAdres());
/* 104:129 */     this.joinGame = new Button("Join game");
/* 105:130 */     this.joinGame.setTheme("button");
/* 106:131 */     this.ips = new ListBox();
/* 107:132 */     this.ips.addCallback(new CallbackWithReason()
/* 108:    */     {
/* 109:    */       public void callback(ListBox.CallbackReason reason)
/* 110:    */       {
/* 111:137 */         int index = NewGameState.this.ips.getSelected();
/* 112:139 */         if ((index >= 0) && (index < NewGameState.this.addresses.size())) {
/* 113:141 */           NewGameState.this.address.setText(((InetAddress)NewGameState.this.addresses.get(index)).getHostAddress());
/* 114:    */         }
/* 115:    */       }
/* 116:146 */     });
/* 117:147 */     this.joinGame.addCallback(new Runnable()
/* 118:    */     {
/* 119:    */       public void run()
/* 120:    */       {
/* 121:151 */         String ip = NewGameState.this.address.getText();
/* 122:152 */         if ((ip != null) && (ip != ""))
/* 123:    */         {
/* 124:154 */           NewGameState.this.container.setServer(false);
/* 125:    */           
/* 126:156 */           String result = NewGameState.this.container.connectClient(ip);
/* 127:157 */           if (result == null)
/* 128:    */           {
/* 129:159 */             NewGameState.this.container.playSound("click");
/* 130:160 */             NewGameState.this.container.setName(NewGameState.this.userName.getText());
/* 131:161 */             NewGameState.this.container.AddClientListener(NewGameState.this.clientListener);
/* 132:162 */             PlayerJoined player = new PlayerJoined();
/* 133:163 */             player.naam = NewGameState.this.container.getName();
/* 134:164 */             NewGameState.this.container.send(player);
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:175 */     });
/* 139:176 */     this.hostGame = new Button("Host game");
/* 140:177 */     this.hostGame.setTheme("button");
/* 141:178 */     this.hostGame.addCallback(new Runnable()
/* 142:    */     {
/* 143:    */       public void run()
/* 144:    */       {
/* 145:182 */         NewGameState.this.container.playSound("click");
/* 146:183 */         NewGameState.this.container.setServer(true);
/* 147:184 */         NewGameState.this.container.setName(NewGameState.this.userName.getText());
/* 148:    */         
/* 149:186 */         NewGameState.this.sb.enterState(5);
/* 150:    */       }
/* 151:188 */     });
/* 152:189 */     rp.add(this.ips);
/* 153:190 */     rp.add(this.ipAddressLabel);
/* 154:191 */     rp.add(this.address);
/* 155:192 */     rp.add(this.refresh);
/* 156:193 */     rp.add(this.joinGame);
/* 157:194 */     rp.add(this.hostGame);
/* 158:195 */     rp.add(this.userName);
/* 159:196 */     rp.add(this.searching);
/* 160:197 */     rp.add(this.back);
/* 161:198 */     return rp;
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected void layoutRootPane()
/* 165:    */   {
/* 166:204 */     int middleX = this.container.getWidth() / 2;
/* 167:205 */     int middleY = this.container.getHeight() / 2;
/* 168:    */     
/* 169:207 */     int maxX = this.container.getWidth();
/* 170:208 */     int maxY = this.container.getHeight();
/* 171:    */     
/* 172:210 */     this.userName.setPosition(100, 50);
/* 173:211 */     this.userName.setSize(100, 20);
/* 174:    */     
/* 175:213 */     this.searching.setPosition(220, 50);
/* 176:214 */     this.searching.setSize(100, 20);
/* 177:    */     
/* 178:216 */     this.ips.setPosition(100, 100);
/* 179:217 */     this.ips.setSize(200, maxY - 200);
/* 180:218 */     this.joinGame.setSize(100, 20);
/* 181:219 */     this.joinGame.setPosition(410, maxY - 50);
/* 182:    */     
/* 183:    */ 
/* 184:222 */     this.address.setSize(100, 20);
/* 185:223 */     this.address.setPosition(290, maxY - 50);
/* 186:    */     
/* 187:225 */     this.hostGame.setSize(100, 20);
/* 188:226 */     this.hostGame.setPosition(530, maxY - 50);
/* 189:    */     
/* 190:228 */     this.refresh.setSize(100, 20);
/* 191:229 */     this.refresh.setPosition(650, maxY - 50);
/* 192:    */     
/* 193:    */ 
/* 194:232 */     this.ipAddressLabel.setSize(100, 20);
/* 195:233 */     this.ipAddressLabel.setPosition(170, maxY - 50);
/* 196:234 */     this.back.setPosition(50, maxY - 50);
/* 197:235 */     this.back.setSize(100, 20);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
/* 201:    */     throws SlickException
/* 202:    */   {}
/* 203:    */   
/* 204:    */   public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
/* 205:    */     throws SlickException
/* 206:    */   {
/* 207:253 */     if (this.hasMap)
/* 208:    */     {
/* 209:255 */       this.container.removeClientListener(this.clientListener);
/* 210:256 */       this.sb.enterState(1);
/* 211:    */     }
/* 212:258 */     if ((this.scanForHosts) && (this.ips != null))
/* 213:    */     {
/* 214:260 */       final Client temp = this.container.getClient();
/* 215:261 */       if (temp != null)
/* 216:    */       {
/* 217:263 */         this.searching.setText("Status: Searching");
/* 218:264 */         this.scanForHosts = false;
/* 219:    */         
/* 220:266 */         Runnable myRunnable = new Runnable()
/* 221:    */         {
/* 222:    */           public void run()
/* 223:    */           {
/* 224:271 */             NewGameState.this.addresses = temp.discoverHosts(54777, 1000);
/* 225:272 */             SimpleChangableListModel<String> model = new SimpleChangableListModel();
/* 226:273 */             for (int i = 0; i < NewGameState.this.addresses.size(); i++)
/* 227:    */             {
/* 228:275 */               System.out.println(((InetAddress)NewGameState.this.addresses.get(i)).getHostAddress());
/* 229:276 */               model.addElement(((InetAddress)NewGameState.this.addresses.get(i)).getHostAddress());
/* 230:    */             }
/* 231:278 */             NewGameState.this.ips.setModel(model);
/* 232:279 */             NewGameState.this.searching.setText("Status: Done");
/* 233:    */           }
/* 234:281 */         };
/* 235:282 */         Thread thread = new Thread(myRunnable);
/* 236:283 */         thread.start();
/* 237:    */       }
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public class JoinCLientListener
/* 242:    */     extends Listener
/* 243:    */   {
/* 244:    */     public JoinCLientListener() {}
/* 245:    */     
/* 246:    */     public void connected(Connection arg0)
/* 247:    */     {
/* 248:296 */       super.connected(arg0);
/* 249:    */     }
/* 250:    */     
/* 251:    */     public void received(Connection connection, Object object)
/* 252:    */     {
/* 253:302 */       if ((object instanceof MapName))
/* 254:    */       {
/* 255:304 */         MapName map = (MapName)object;
/* 256:    */         
/* 257:306 */         NewGameState.this.container.setMap2(map.mapName);
/* 258:307 */         NewGameState.this.container.setConnectionID(map.connectionID);
/* 259:308 */         System.out.println("MAP: " + map.mapName);
/* 260:309 */         NewGameState.this.hasMap = true;
/* 261:    */       }
/* 262:    */     }
/* 263:    */   }
/* 264:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.NewGameState
 * JD-Core Version:    0.7.0.1
 */