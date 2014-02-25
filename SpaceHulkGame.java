/*   1:    */ 
/*   2:    */ 
/*   3:    */ java.io.File
/*   4:    */ java.io.FileInputStream
/*   5:    */ java.io.FileNotFoundException
/*   6:    */ java.io.IOException
/*   7:    */ java.io.PrintStream
/*   8:    */ java.net.MalformedURLException
/*   9:    */ java.net.URL
/*  10:    */ java.util.Properties
/*  11:    */ org.newdawn.slick.GameContainer
/*  12:    */ org.newdawn.slick.SlickException
/*  13:    */ org.newdawn.slick.state.GameState
/*  14:    */ sh.gui.TWL.TWLStateBasedGame
/*  15:    */ sh.properties.GameProperties
/*  16:    */ sh.states.AlienGamePlayState
/*  17:    */ sh.states.EndGameState
/*  18:    */ sh.states.HostGameState
/*  19:    */ sh.states.MainMenuState
/*  20:    */ sh.states.MarineGamePlayState
/*  21:    */ sh.states.NetworkStartState
/*  22:    */ sh.states.NewGameState
/*  23:    */ sh.states.OptionsState
/*  24:    */ 
/*  25:    */ SpaceHulkGame
/*  26:    */   
/*  27:    */ 
/*  28:    */   width
/*  29:    */   height
/*  30:    */   MAINMENUSTATE = 0
/*  31:    */   NETWORKSTARTSTATE = 1
/*  32:    */   MARINEGAMEPLAYSTATE = 2
/*  33:    */   ALIENGAMEPLAYSTATE = 3
/*  34:    */   NEWGAMESTATE = 4
/*  35:    */   HOSTGAMESTATE = 5
/*  36:    */   ENDGAMESTATE = 6
/*  37:    */   OPTIONSTATE = 7
/*  38:    */   
/*  39:    */   getWidth
/*  40:    */   
/*  41: 63 */     width;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getHeight()
/*  45:    */   {
/*  46: 68 */     return this.height;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public SpaceHulkGame(String name)
/*  50:    */   {
/*  51: 73 */     super(name);
/*  52: 74 */     this.width = 800;
/*  53: 75 */     this.height = 600;
/*  54:    */     
/*  55: 77 */     addState(new MainMenuState(0));
/*  56: 78 */     addState(new NetworkStartState(1));
/*  57:    */     
/*  58: 80 */     addState(new MarineGamePlayState(2));
/*  59: 81 */     addState(new AlienGamePlayState(3));
/*  60: 82 */     addState(new NewGameState(4));
/*  61: 83 */     addState(new HostGameState(5));
/*  62: 84 */     addState(new EndGameState(6));
/*  63: 85 */     addState(new OptionsState(7));
/*  64: 86 */     enterState(0);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void main(String[] args)
/*  68:    */   {
/*  69: 96 */     SpaceHulkGame game = new SpaceHulkGame("Spaxe Hilk");
/*  70:    */     
/*  71: 98 */     boolean server = false;
/*  72: 99 */     String adres = "127.0.0.1";
/*  73:100 */     String faction = "marines";
/*  74:101 */     String name = "Michel";
/*  75:102 */     String map = "data/maps/SH1.tmx";
/*  76:    */     try
/*  77:    */     {
/*  78:105 */       GameProperties gproperties = new GameProperties();
/*  79:106 */       Properties prop = new Properties();
/*  80:    */       try
/*  81:    */       {
/*  82:110 */         prop.load(new FileInputStream(args[0]));
/*  83:    */         
/*  84:112 */         gproperties.adres = prop.getProperty("adres");
/*  85:113 */         gproperties.name = prop.getProperty("name");
/*  86:114 */         gproperties.music = Integer.parseInt(prop.getProperty("music", "50"));
/*  87:115 */         gproperties.sound = Integer.parseInt(prop.getProperty("sound", "50"));
/*  88:116 */         gproperties.width = Integer.parseInt(prop.getProperty("width", "0"));
/*  89:117 */         gproperties.height = Integer.parseInt(prop.getProperty("height", "0"));
/*  90:118 */         gproperties.fullscreen = Boolean.parseBoolean(prop.getProperty("fullscreen", "true"));
/*  91:119 */         gproperties.propertiesfile = args[0];
/*  92:    */       }
/*  93:    */       catch (FileNotFoundException e)
/*  94:    */       {
/*  95:125 */         e.printStackTrace();
/*  96:    */       }
/*  97:    */       catch (IOException e)
/*  98:    */       {
/*  99:129 */         e.printStackTrace();
/* 100:    */       }
/* 101:132 */       SpaceHulkGameContainer container = new SpaceHulkGameContainer(game, gproperties, prop);
/* 102:    */       
/* 103:134 */       container.setUpdateOnlyWhenVisible(false);
/* 104:135 */       container.setAlwaysRender(true);
/* 105:136 */       container.setVSync(true);
/* 106:    */       
/* 107:138 */       container.start();
/* 108:    */     }
/* 109:    */     catch (SlickException e)
/* 110:    */     {
/* 111:142 */       e.printStackTrace();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void initStatesList(GameContainer gameContainer)
/* 116:    */     throws SlickException
/* 117:    */   {
/* 118:151 */     System.out.println("Initializing");
/* 119:152 */     getState(0).init(gameContainer, this);
/* 120:153 */     getState(4).init(gameContainer, this);
/* 121:154 */     getState(1).init(gameContainer, this);
/* 122:155 */     getState(2).init(gameContainer, this);
/* 123:156 */     getState(3).init(gameContainer, this);
/* 124:157 */     getState(5).init(gameContainer, this);
/* 125:158 */     getState(7).init(gameContainer, this);
/* 126:159 */     getState(6).init(gameContainer, this);
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected URL getThemeURL()
/* 130:    */   {
/* 131:166 */     File test = new File("data/gui/guiTheme.xml");
/* 132:    */     
/* 133:168 */     URL url = null;
/* 134:    */     try
/* 135:    */     {
/* 136:171 */       url = test.toURL();
/* 137:    */     }
/* 138:    */     catch (MalformedURLException e)
/* 139:    */     {
/* 140:175 */       e.printStackTrace();
/* 141:    */     }
/* 142:178 */     return url;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.SpaceHulkGame
 * JD-Core Version:    0.7.0.1
 */