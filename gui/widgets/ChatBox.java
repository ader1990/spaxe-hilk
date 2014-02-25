/*   1:    */ package sh.gui.widgets;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.DialogLayout;
/*   4:    */ import de.matthiasmann.twl.EditField;
/*   5:    */ import de.matthiasmann.twl.EditField.Callback;
/*   6:    */ import de.matthiasmann.twl.ResizableFrame;
/*   7:    */ import de.matthiasmann.twl.ScrollPane;
/*   8:    */ import de.matthiasmann.twl.ScrollPane.Fixed;
/*   9:    */ import de.matthiasmann.twl.TextArea;
/*  10:    */ import de.matthiasmann.twl.TextArea.Callback;
/*  11:    */ import de.matthiasmann.twl.Widget;
/*  12:    */ import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
/*  13:    */ import org.lwjgl.Sys;
/*  14:    */ import sh.SpaceHulkGameContainer;
/*  15:    */ import sh.multiplayer.ChatMessage;
/*  16:    */ 
/*  17:    */ public class ChatBox
/*  18:    */   extends ResizableFrame
/*  19:    */ {
/*  20:    */   private SpaceHulkGameContainer container;
/*  21:    */   private StringBuilder sb;
/*  22:    */   private HTMLTextAreaModel textAreaModel;
/*  23:    */   private TextArea textArea;
/*  24:    */   private EditField editField;
/*  25:    */   private ScrollPane scrollPane;
/*  26:    */   
/*  27:    */   public ChatBox(SpaceHulkGameContainer shgameContainer)
/*  28:    */   {
/*  29: 34 */     setTitle("Chat");
/*  30:    */     
/*  31: 36 */     this.container = shgameContainer;
/*  32: 37 */     this.sb = new StringBuilder();
/*  33: 38 */     this.textAreaModel = new HTMLTextAreaModel();
/*  34: 39 */     this.textArea = new TextArea(this.textAreaModel);
/*  35: 40 */     this.editField = new EditField();
/*  36:    */     
/*  37: 42 */     this.editField.addCallback(new EditField.Callback()
/*  38:    */     {
/*  39:    */       public void callback(int key)
/*  40:    */       {
/*  41: 44 */         if (key == 28)
/*  42:    */         {
/*  43: 45 */           ChatMessage message = new ChatMessage();
/*  44: 46 */           message.name = ChatBox.this.container.getName();
/*  45: 47 */           message.text = ChatBox.this.editField.getText();
/*  46: 48 */           ChatBox.this.container.send(message);
/*  47:    */           
/*  48: 50 */           ChatBox.this.appendRow(ChatBox.this.container.getName(), ChatBox.this.editField.getText());
/*  49: 51 */           ChatBox.this.editField.setText("");
/*  50:    */         }
/*  51:    */       }
/*  52: 56 */     });
/*  53: 57 */     this.textArea.addCallback(new TextArea.Callback()
/*  54:    */     {
/*  55:    */       public void handleLinkClicked(String href)
/*  56:    */       {
/*  57: 59 */         Sys.openURL(href);
/*  58:    */       }
/*  59: 61 */     });
/*  60: 62 */     this.scrollPane = new ScrollPane(this.textArea);
/*  61: 63 */     this.scrollPane.setFixed(ScrollPane.Fixed.HORIZONTAL);
/*  62:    */     
/*  63: 65 */     DialogLayout l = new DialogLayout();
/*  64: 66 */     l.setTheme("content");
/*  65: 67 */     l.setHorizontalGroup(l.createParallelGroup(new Widget[] { this.scrollPane, this.editField }));
/*  66: 68 */     l.setVerticalGroup(l.createSequentialGroup(new Widget[] { this.scrollPane, this.editField }));
/*  67: 69 */     add(l);
/*  68: 70 */     appendRow("Spaxe Hilk", "Welcome to Spaxe Hilk. You can chat with your fellow players below");
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void appendRow(String name, String text)
/*  72:    */   {
/*  73: 75 */     this.sb.append("<div style=\"word-wrap: break-word;  \">");
/*  74: 76 */     this.sb.append("&lt;" + name + "&gt; ");
/*  75: 77 */     int i = 0;
/*  76: 77 */     for (int l = text.length(); i < l; i++)
/*  77:    */     {
/*  78: 78 */       char ch = text.charAt(i);
/*  79: 79 */       switch (ch)
/*  80:    */       {
/*  81:    */       case '<': 
/*  82: 80 */         this.sb.append("&lt;"); break;
/*  83:    */       case '>': 
/*  84: 81 */         this.sb.append("&gt;"); break;
/*  85:    */       case '&': 
/*  86: 82 */         this.sb.append("&amp;"); break;
/*  87:    */       case '"': 
/*  88: 83 */         this.sb.append("&quot;"); break;
/*  89:    */       case 'h': 
/*  90: 85 */         if (text.startsWith("http://", i))
/*  91:    */         {
/*  92: 86 */           int end = i + 7;
/*  93: 87 */           while ((end < l) && (isURLChar(text.charAt(end)))) {
/*  94: 88 */             end++;
/*  95:    */           }
/*  96: 90 */           String href = text.substring(i, end);
/*  97: 91 */           this.sb.append("<a style=\"font: link\" href=\"").append(href)
/*  98: 92 */             .append("\" >").append(href)
/*  99: 93 */             .append("</a>");
/* 100: 94 */           i = end - 1;
/* 101:    */         }
/* 102: 95 */         break;
/* 103:    */       }
/* 104: 99 */       this.sb.append(ch);
/* 105:    */     }
/* 106:102 */     this.sb.append("</div>");
/* 107:    */     
/* 108:104 */     boolean isAtEnd = this.scrollPane.getMaxScrollPosY() == this.scrollPane.getScrollPositionY();
/* 109:    */     
/* 110:106 */     this.textAreaModel.setHtml(this.sb.toString());
/* 111:108 */     if (isAtEnd)
/* 112:    */     {
/* 113:109 */       this.scrollPane.validateLayout();
/* 114:110 */       this.scrollPane.setScrollPositionY(this.scrollPane.getMaxScrollPosY());
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private boolean isURLChar(char ch)
/* 119:    */   {
/* 120:114 */     return (ch == '.') || (ch == '/') || (ch == '%') || 
/* 121:115 */       ((ch >= '0') && (ch <= '9')) || 
/* 122:116 */       ((ch >= 'a') && (ch <= 'z')) || (
/* 123:117 */       (ch >= 'A') && (ch <= 'Z'));
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.ChatBox
 * JD-Core Version:    0.7.0.1
 */