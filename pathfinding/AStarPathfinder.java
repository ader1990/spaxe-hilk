/*   1:    */ package sh.pathfinding;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.PriorityQueue;
/*   9:    */ import java.util.Queue;
/*  10:    */ import org.newdawn.slick.geom.Vector2f;
/*  11:    */ import sh.world.TileBasedWorldModel;
/*  12:    */ import sh.world.WorldModel;
/*  13:    */ 
/*  14:    */ public class AStarPathfinder
/*  15:    */ {
/*  16: 17 */   private Queue<Node> open = new PriorityQueue(20, new NodeComparator(null));
/*  17: 18 */   private List<Node> closed = new ArrayList();
/*  18: 20 */   private TileBasedWorldModel worldModel = null;
/*  19:    */   
/*  20:    */   public AStarPathfinder(TileBasedWorldModel worldModel2)
/*  21:    */   {
/*  22: 23 */     this.worldModel = worldModel2;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Path findPath(float sx, float sy, float tx, float ty)
/*  26:    */   {
/*  27: 28 */     if (this.worldModel.tileBlocked(tx, ty)) {
/*  28: 29 */       return null;
/*  29:    */     }
/*  30: 32 */     Node goal = new Node((int)tx, (int)ty);
/*  31: 33 */     Node start = new Node((int)sx, (int)sy);
/*  32:    */     
/*  33: 35 */     this.open.clear();
/*  34: 36 */     this.closed.clear();
/*  35:    */     
/*  36: 38 */     this.open.add(start);
/*  37:    */     Iterator localIterator;
/*  38: 40 */     for (; !this.open.isEmpty(); localIterator.hasNext())
/*  39:    */     {
/*  40: 41 */       Node current = (Node)this.open.peek();
/*  41: 43 */       if (current.equals(goal))
/*  42:    */       {
/*  43: 44 */         goal.parent = current.parent;
/*  44: 45 */         break;
/*  45:    */       }
/*  46: 48 */       this.open.remove(current);
/*  47: 49 */       this.closed.add(current);
/*  48:    */       
/*  49: 51 */       List<Node> neighbors = getNeighbors(current);
/*  50:    */       
/*  51: 53 */       localIterator = neighbors.iterator(); continue;Node neighbor = (Node)localIterator.next();
/*  52: 55 */       if (!this.worldModel.tileBlocked(neighbor.x, neighbor.y))
/*  53:    */       {
/*  54: 59 */         float g = current.g + getMovementCost(current, neighbor);
/*  55: 61 */         if ((this.open.contains(neighbor)) && (g < neighbor.g))
/*  56:    */         {
/*  57: 62 */           neighbor.g = g;
/*  58: 63 */           neighbor.parent = current;
/*  59:    */         }
/*  60: 66 */         if ((this.closed.contains(neighbor)) && (g < neighbor.g))
/*  61:    */         {
/*  62: 67 */           neighbor.g = g;
/*  63: 68 */           neighbor.parent = current;
/*  64:    */         }
/*  65: 71 */         if ((!this.open.contains(neighbor)) && (!this.closed.contains(neighbor)))
/*  66:    */         {
/*  67: 72 */           neighbor.g = g;
/*  68: 73 */           neighbor.h = getHeuristicCost(neighbor, goal);
/*  69: 74 */           neighbor.parent = current;
/*  70:    */           
/*  71: 76 */           this.open.add(neighbor);
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75: 81 */     if (goal.parent != null) {
/*  76: 82 */       return reconstructPath(goal);
/*  77:    */     }
/*  78: 84 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Path reconstructPath(Node node)
/*  82:    */   {
/*  83: 89 */     Path path = new Path();
/*  84:    */     
/*  85: 91 */     int x = node.x;
/*  86: 92 */     int y = node.y;
/*  87: 94 */     while (node.parent != null)
/*  88:    */     {
/*  89: 95 */       node = node.parent;
/*  90:    */       
/*  91: 97 */       path.pushSegment(new PathSegment(node.x + 0.5F, node.y + 0.5F, x + 0.5F, y + 0.5F));
/*  92:    */       
/*  93: 99 */       x = node.x;
/*  94:100 */       y = node.y;
/*  95:    */     }
/*  96:103 */     return path;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private float getMovementCost(Node from, Node to)
/* 100:    */   {
/* 101:107 */     return WorldModel.getEuclideanDistance(from.x, from.y, to.x, to.y);
/* 102:    */   }
/* 103:    */   
/* 104:    */   private float getHeuristicCost(Node from, Node to)
/* 105:    */   {
/* 106:111 */     return WorldModel.getEuclideanDistance(from.x, from.y, to.x, to.y);
/* 107:    */   }
/* 108:    */   
/* 109:    */   private List<Node> getNeighbors(Node node)
/* 110:    */   {
/* 111:115 */     List<Node> neighbors = new ArrayList();
/* 112:    */     
/* 113:117 */     neighbors.add(new Node(node.x - 1, node.y - 1));
/* 114:118 */     neighbors.add(new Node(node.x, node.y - 1));
/* 115:119 */     neighbors.add(new Node(node.x + 1, node.y - 1));
/* 116:120 */     neighbors.add(new Node(node.x - 1, node.y));
/* 117:121 */     neighbors.add(new Node(node.x + 1, node.y));
/* 118:122 */     neighbors.add(new Node(node.x - 1, node.y + 1));
/* 119:123 */     neighbors.add(new Node(node.x, node.y + 1));
/* 120:124 */     neighbors.add(new Node(node.x + 1, node.y + 1));
/* 121:    */     
/* 122:126 */     return neighbors;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private class Node
/* 126:    */   {
/* 127:    */     public int x;
/* 128:    */     public int y;
/* 129:134 */     public float g = 0.0F;
/* 130:135 */     public float h = 0.0F;
/* 131:137 */     public Node parent = null;
/* 132:    */     
/* 133:    */     public Node(int x, int y)
/* 134:    */     {
/* 135:140 */       this.x = x;
/* 136:141 */       this.y = y;
/* 137:    */     }
/* 138:    */     
/* 139:    */     public float getF()
/* 140:    */     {
/* 141:145 */       return this.g + this.h;
/* 142:    */     }
/* 143:    */     
/* 144:    */     public boolean equals(Object object)
/* 145:    */     {
/* 146:150 */       if ((object instanceof Node)) {
/* 147:151 */         return (((Node)object).x == this.x) && (((Node)object).y == this.y);
/* 148:    */       }
/* 149:154 */       return false;
/* 150:    */     }
/* 151:    */     
/* 152:    */     public String toString()
/* 153:    */     {
/* 154:159 */       return String.format("(%s,%s), g: %s, h: %s", new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Float.valueOf(this.g), Float.valueOf(this.h) });
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   private class NodeComparator
/* 159:    */     implements Comparator<AStarPathfinder.Node>
/* 160:    */   {
/* 161:    */     private NodeComparator() {}
/* 162:    */     
/* 163:    */     public int compare(AStarPathfinder.Node node1, AStarPathfinder.Node node2)
/* 164:    */     {
/* 165:167 */       float f1 = node1.getF();
/* 166:168 */       float f2 = node2.getF();
/* 167:    */       
/* 168:170 */       return f1 > f2 ? 1 : f1 < f2 ? -1 : 0;
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public class Path
/* 173:    */   {
/* 174:176 */     private LinkedList<AStarPathfinder.PathSegment> segments = new LinkedList();
/* 175:    */     
/* 176:    */     public Path() {}
/* 177:    */     
/* 178:    */     public void addSegment(AStarPathfinder.PathSegment segment)
/* 179:    */     {
/* 180:183 */       this.segments.add(segment);
/* 181:    */     }
/* 182:    */     
/* 183:    */     public void pushSegment(AStarPathfinder.PathSegment segment)
/* 184:    */     {
/* 185:187 */       this.segments.push(segment);
/* 186:    */     }
/* 187:    */     
/* 188:    */     public List<AStarPathfinder.PathSegment> getSegments()
/* 189:    */     {
/* 190:191 */       return this.segments;
/* 191:    */     }
/* 192:    */     
/* 193:    */     public Vector2f project(Vector2f point)
/* 194:    */     {
/* 195:201 */       float minDistance = 3.4028235E+38F;
/* 196:202 */       Vector2f minProjection = null;
/* 197:204 */       for (AStarPathfinder.PathSegment segment : this.segments)
/* 198:    */       {
/* 199:205 */         Vector2f projection = segment.project(point);
/* 200:206 */         float distance = point.distance(projection);
/* 201:208 */         if (distance < minDistance)
/* 202:    */         {
/* 203:209 */           minDistance = distance;
/* 204:210 */           minProjection = projection;
/* 205:    */         }
/* 206:    */       }
/* 207:214 */       return minProjection;
/* 208:    */     }
/* 209:    */     
/* 210:    */     public float distanceFromPath(Vector2f point)
/* 211:    */     {
/* 212:224 */       float minDistance = 3.4028235E+38F;
/* 213:226 */       for (AStarPathfinder.PathSegment segment : this.segments)
/* 214:    */       {
/* 215:227 */         float distance = segment.distance(point);
/* 216:229 */         if (distance < minDistance) {
/* 217:230 */           minDistance = distance;
/* 218:    */         }
/* 219:    */       }
/* 220:234 */       return minDistance;
/* 221:    */     }
/* 222:    */     
/* 223:    */     public float distanceOnPath(Vector2f point)
/* 224:    */     {
/* 225:244 */       float minDistance = 3.4028235E+38F;
/* 226:    */       
/* 227:246 */       float pathDistance = 0.0F;
/* 228:247 */       float totalDistance = 0.0F;
/* 229:249 */       for (AStarPathfinder.PathSegment segment : this.segments)
/* 230:    */       {
/* 231:250 */         float distance = segment.distance(point);
/* 232:252 */         if (distance < minDistance)
/* 233:    */         {
/* 234:253 */           minDistance = distance;
/* 235:254 */           totalDistance = pathDistance + segment.getScalar(point);
/* 236:    */         }
/* 237:257 */         pathDistance += segment.length();
/* 238:    */       }
/* 239:260 */       return totalDistance;
/* 240:    */     }
/* 241:    */     
/* 242:    */     public Vector2f pointOnPath(float distance)
/* 243:    */     {
/* 244:270 */       for (AStarPathfinder.PathSegment segment : this.segments)
/* 245:    */       {
/* 246:271 */         float segmentLength = segment.length();
/* 247:273 */         if (segmentLength >= distance)
/* 248:    */         {
/* 249:274 */           float ratio = distance / segmentLength;
/* 250:275 */           return new Vector2f(segment.x1 + ratio * (segment.x2 - segment.x1), segment.y1 + ratio * (segment.y2 - segment.y1));
/* 251:    */         }
/* 252:278 */         distance -= segmentLength;
/* 253:    */       }
/* 254:281 */       return new Vector2f(((AStarPathfinder.PathSegment)this.segments.getLast()).x2, ((AStarPathfinder.PathSegment)this.segments.getLast()).y2);
/* 255:    */     }
/* 256:    */     
/* 257:    */     public float length()
/* 258:    */     {
/* 259:289 */       float length = 0.0F;
/* 260:291 */       for (AStarPathfinder.PathSegment segment : this.segments) {
/* 261:292 */         length += segment.length();
/* 262:    */       }
/* 263:295 */       return length;
/* 264:    */     }
/* 265:    */     
/* 266:    */     public Vector2f getTangent(Vector2f point)
/* 267:    */     {
/* 268:305 */       Vector2f tangent = new Vector2f();
/* 269:306 */       float minDistance = 3.4028235E+38F;
/* 270:308 */       for (AStarPathfinder.PathSegment segment : this.segments)
/* 271:    */       {
/* 272:309 */         float distance = segment.distance(point);
/* 273:311 */         if (distance < minDistance)
/* 274:    */         {
/* 275:312 */           minDistance = distance;
/* 276:313 */           tangent = segment.getUnitVector();
/* 277:    */         }
/* 278:    */       }
/* 279:317 */       return tangent;
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public class PathSegment
/* 284:    */   {
/* 285:    */     public final float x1;
/* 286:    */     public final float y1;
/* 287:    */     public final float x2;
/* 288:    */     public final float y2;
/* 289:    */     
/* 290:    */     public PathSegment(float x1, float y1, float x2, float y2)
/* 291:    */     {
/* 292:330 */       this.x1 = x1;
/* 293:331 */       this.y1 = y1;
/* 294:    */       
/* 295:333 */       this.x2 = x2;
/* 296:334 */       this.y2 = y2;
/* 297:    */     }
/* 298:    */     
/* 299:    */     public float length()
/* 300:    */     {
/* 301:343 */       return getVector().length();
/* 302:    */     }
/* 303:    */     
/* 304:    */     public float distance(Vector2f point)
/* 305:    */     {
/* 306:353 */       return project(point).distance(point);
/* 307:    */     }
/* 308:    */     
/* 309:    */     public Vector2f project(Vector2f point)
/* 310:    */     {
/* 311:363 */       Vector2f segmentVector = getVector();
/* 312:364 */       Vector2f pointVector = point.copy().sub(getP1());
/* 313:    */       
/* 314:366 */       float segmentLenght = segmentVector.length();
/* 315:    */       
/* 316:368 */       segmentVector.normalise();
/* 317:    */       
/* 318:370 */       float scalarProjection = segmentVector.dot(pointVector);
/* 319:372 */       if (scalarProjection < 0.0F) {
/* 320:373 */         return getP1();
/* 321:    */       }
/* 322:374 */       if (scalarProjection > segmentLenght) {
/* 323:375 */         return getP2();
/* 324:    */       }
/* 325:377 */       segmentVector.scale(scalarProjection);
/* 326:378 */       segmentVector.add(getP1());
/* 327:379 */       return segmentVector;
/* 328:    */     }
/* 329:    */     
/* 330:    */     public float getScalar(Vector2f point)
/* 331:    */     {
/* 332:384 */       Vector2f pointVector = point.copy().sub(getP1());
/* 333:385 */       Vector2f segmentVector = getVector();
/* 334:    */       
/* 335:387 */       float segmentLenght = segmentVector.length();
/* 336:    */       
/* 337:389 */       segmentVector.normalise();
/* 338:    */       
/* 339:391 */       float scalarProjection = segmentVector.dot(pointVector);
/* 340:393 */       if (scalarProjection < 0.0F) {
/* 341:394 */         scalarProjection = 0.0F;
/* 342:395 */       } else if (scalarProjection > segmentLenght) {
/* 343:396 */         scalarProjection = segmentLenght;
/* 344:    */       }
/* 345:399 */       return scalarProjection;
/* 346:    */     }
/* 347:    */     
/* 348:    */     public Vector2f getUnitVector()
/* 349:    */     {
/* 350:407 */       return getVector().normalise();
/* 351:    */     }
/* 352:    */     
/* 353:    */     public Vector2f getVector()
/* 354:    */     {
/* 355:415 */       return getP2().sub(getP1());
/* 356:    */     }
/* 357:    */     
/* 358:    */     public Vector2f getP1()
/* 359:    */     {
/* 360:423 */       return new Vector2f(this.x1, this.y1);
/* 361:    */     }
/* 362:    */     
/* 363:    */     public Vector2f getP2()
/* 364:    */     {
/* 365:431 */       return new Vector2f(this.x2, this.y2);
/* 366:    */     }
/* 367:    */   }
/* 368:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.pathfinding.AStarPathfinder
 * JD-Core Version:    0.7.0.1
 */