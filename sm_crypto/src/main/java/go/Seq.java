/*     */ package go;
/*     */ 
/*     */ import android.content.Context;
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Seq
/*     */ {
/*  25 */   private static Logger log = Logger.getLogger("GoSeq");
/*     */ 
/*     */   
/*     */   private static final int NULL_REFNUM = 41;
/*     */ 
/*     */   
/*  31 */   public static final Ref nullRef = new Ref(41, null);
/*     */ 
/*     */   
/*  34 */   private static final GoRefQueue goRefQueue = new GoRefQueue();
/*     */   
/*     */   static {
/*  37 */     System.loadLibrary("gojni");
/*  38 */     init();
/*  39 */     Universe.touch();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setContext(Context paramContext) {
/*  44 */     setContext(paramContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void touch() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void incRefnum(int paramInt) {
/*  59 */     tracker.incRefnum(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int incRef(Object paramObject) {
/*  67 */     return tracker.inc(paramObject);
/*     */   }
/*     */   
/*     */   public static int incGoObjectRef(GoObject paramGoObject) {
/*  71 */     return paramGoObject.incRefnum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void trackGoRef(int paramInt, GoObject paramGoObject) {
/*  85 */     if (paramInt > 0) {
/*  86 */       throw new RuntimeException("trackGoRef called with Java refnum " + paramInt);
/*     */     }
/*  88 */     goRefQueue.track(paramInt, paramGoObject);
/*     */   }
/*     */   
/*     */   public static Ref getRef(int paramInt) {
/*  92 */     return tracker.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void decRef(int paramInt) {
/* 106 */     tracker.dec(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface GoObject
/*     */   {
/*     */     int incRefnum();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Proxy
/*     */     extends GoObject {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Ref
/*     */   {
/*     */     public final int refnum;
/*     */ 
/*     */     
/*     */     private int refcnt;
/*     */ 
/*     */     
/*     */     public final Object obj;
/*     */ 
/*     */     
/*     */     Ref(int param1Int, Object param1Object) {
/* 135 */       if (param1Int < 0) {
/* 136 */         throw new RuntimeException("Ref instantiated with a Go refnum " + param1Int);
/*     */       }
/* 138 */       this.refnum = param1Int;
/* 139 */       this.refcnt = 0;
/* 140 */       this.obj = param1Object;
/*     */     }
/*     */ 
/*     */     
/*     */     void inc() {
/* 145 */       if (this.refcnt == Integer.MAX_VALUE) {
/* 146 */         throw new RuntimeException("refnum " + this.refnum + " overflow");
/*     */       }
/* 148 */       this.refcnt++;
/*     */     }
/*     */   }
/*     */   
/* 152 */   static final RefTracker tracker = new RefTracker();
/*     */   
/*     */   private static native void init();
/*     */   
/*     */   static native void setContext(Object paramObject);
/*     */   
/*     */   public static native void incGoRef(int paramInt, GoObject paramGoObject);
/*     */   
/*     */   static native void destroyRef(int paramInt);
/*     */   
/* 162 */   static final class RefTracker { private int next = 42;
/*     */ 
/*     */     
/*     */     private static final int REF_OFFSET = 42;
/*     */ 
/*     */     
/* 168 */     private final RefMap javaObjs = new RefMap();
/*     */ 
/*     */     
/* 171 */     private final IdentityHashMap<Object, Integer> javaRefs = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized int inc(Object param1Object) {
/* 176 */       if (param1Object == null) {
/* 177 */         return 41;
/*     */       }
/* 179 */       if (param1Object instanceof Proxy) {
/* 180 */         return ((Proxy)param1Object).incRefnum();
/*     */       }
/* 182 */       Integer integer = this.javaRefs.get(param1Object);
/* 183 */       if (integer == null) {
/* 184 */         if (this.next == Integer.MAX_VALUE) {
/* 185 */           throw new RuntimeException("createRef overflow for " + param1Object);
/*     */         }
/* 187 */         integer = Integer.valueOf(this.next++);
/* 188 */         this.javaRefs.put(param1Object, integer);
/*     */       } 
/* 190 */       int i = integer.intValue();
/* 191 */       Ref ref = this.javaObjs.get(i);
/* 192 */       if (ref == null) {
/* 193 */         ref = new Ref(i, param1Object);
/* 194 */         this.javaObjs.put(i, ref);
/*     */       } 
/* 196 */       ref.inc();
/* 197 */       return i;
/*     */     }
/*     */     
/*     */     synchronized void incRefnum(int param1Int) {
/* 201 */       Ref ref = this.javaObjs.get(param1Int);
/* 202 */       if (ref == null) {
/* 203 */         throw new RuntimeException("referenced Java object is not found: refnum=" + param1Int);
/*     */       }
/* 205 */       ref.inc();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void dec(int param1Int) {
/* 213 */       if (param1Int <= 0) {
/*     */ 
/*     */         
/* 216 */         Seq.log.severe("dec request for Go object " + param1Int);
/*     */         return;
/*     */       } 
/* 219 */       if (param1Int == Seq.nullRef.refnum) {
/*     */         return;
/*     */       }
/*     */       
/* 223 */       Ref ref = this.javaObjs.get(param1Int);
/* 224 */       if (ref == null) {
/* 225 */         throw new RuntimeException("referenced Java object is not found: refnum=" + param1Int);
/*     */       }
/* 227 */       ref.refcnt--;
/* 228 */       if (ref.refcnt <= 0) {
/* 229 */         this.javaObjs.remove(param1Int);
/* 230 */         this.javaRefs.remove(ref.obj);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     synchronized Ref get(int param1Int) {
/* 236 */       if (param1Int < 0) {
/* 237 */         throw new RuntimeException("ref called with Go refnum " + param1Int);
/*     */       }
/* 239 */       if (param1Int == 41) {
/* 240 */         return Seq.nullRef;
/*     */       }
/* 242 */       Ref ref = this.javaObjs.get(param1Int);
/* 243 */       if (ref == null) {
/* 244 */         throw new RuntimeException("unknown java Ref: " + param1Int);
/*     */       }
/* 246 */       return ref;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class GoRefQueue
/*     */     extends ReferenceQueue<GoObject>
/*     */   {
/* 255 */     private final Collection<Object> refs = Collections.synchronizedCollection(new HashSet<>());
/*     */     
/*     */     void track(int param1Int, GoObject param1GoObject) {
/* 258 */       this.refs.add(new GoRef(param1Int, param1GoObject, this));
/*     */     }
/*     */     
/*     */     GoRefQueue() {
/* 262 */       Thread thread = new Thread(new Runnable() { public void run() {
/*     */               while (true) {
/*     */                 try {
/*     */                   while (true)
/* 266 */                   { GoRef goRef = (GoRef) GoRefQueue.this.remove();
/* 267 */                     GoRefQueue.this.refs.remove(goRef);
/* 268 */                     Seq.destroyRef(goRef.refnum);
/* 269 */                     goRef.clear(); }
                /* 270 */                 } catch (InterruptedException interruptedException) {}
/*     */               } 
/*     */             } }
/*     */         );
/*     */ 
/*     */       
/* 276 */       thread.setDaemon(true);
/* 277 */       thread.setName("GoRefQueue Finalizer Thread");
/* 278 */       thread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class GoRef
/*     */     extends PhantomReference<GoObject>
/*     */   {
/*     */     final int refnum;
/*     */     
/*     */     GoRef(int param1Int, GoObject param1GoObject, GoRefQueue param1GoRefQueue) {
/* 289 */       super(param1GoObject, param1GoRefQueue);
/* 290 */       if (param1Int > 0) {
/* 291 */         throw new RuntimeException("GoRef instantiated with a Java refnum " + param1Int);
/*     */       }
/* 293 */       this.refnum = param1Int;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class RefMap
/*     */   {
/* 301 */     private int next = 0;
/* 302 */     private int live = 0;
/* 303 */     private int[] keys = new int[16];
/* 304 */     private Ref[] objs = new Ref[16];
/*     */ 
/*     */ 
/*     */     
/*     */     Ref get(int param1Int) {
/* 309 */       int i = Arrays.binarySearch(this.keys, 0, this.next, param1Int);
/* 310 */       if (i >= 0) {
/* 311 */         return this.objs[i];
/*     */       }
/* 313 */       return null;
/*     */     }
/*     */     
/*     */     void remove(int param1Int) {
/* 317 */       int i = Arrays.binarySearch(this.keys, 0, this.next, param1Int);
/* 318 */       if (i >= 0 && 
/* 319 */         this.objs[i] != null) {
/* 320 */         this.objs[i] = null;
/* 321 */         this.live--;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void put(int param1Int, Ref param1Ref) {
/* 327 */       if (param1Ref == null) {
/* 328 */         throw new RuntimeException("put a null ref (with key " + param1Int + ")");
/*     */       }
/* 330 */       int i = Arrays.binarySearch(this.keys, 0, this.next, param1Int);
/* 331 */       if (i >= 0) {
/* 332 */         if (this.objs[i] == null) {
/* 333 */           this.objs[i] = param1Ref;
/* 334 */           this.live++;
/*     */         } 
/* 336 */         if (this.objs[i] != param1Ref) {
/* 337 */           throw new RuntimeException("replacing an existing ref (with key " + param1Int + ")");
/*     */         }
/*     */         return;
/*     */       } 
/* 341 */       if (this.next >= this.keys.length) {
/* 342 */         grow();
/* 343 */         i = Arrays.binarySearch(this.keys, 0, this.next, param1Int);
/*     */       } 
/* 345 */       i ^= 0xFFFFFFFF;
/* 346 */       if (i < this.next) {
/*     */         
/* 348 */         System.arraycopy(this.keys, i, this.keys, i + 1, this.next - i);
/* 349 */         System.arraycopy(this.objs, i, this.objs, i + 1, this.next - i);
/*     */       } 
/* 351 */       this.keys[i] = param1Int;
/* 352 */       this.objs[i] = param1Ref;
/* 353 */       this.live++;
/* 354 */       this.next++;
/*     */     }
/*     */ 
/*     */     
/*     */     private void grow() {
/*     */       int[] arrayOfInt;
/*     */       Ref[] arrayOfRef;
/* 361 */       int i = 2 * roundPow2(this.live);
/* 362 */       if (i > this.keys.length) {
/* 363 */         arrayOfInt = new int[this.keys.length * 2];
/* 364 */         arrayOfRef = new Ref[this.objs.length * 2];
/*     */       } else {
/* 366 */         arrayOfInt = this.keys;
/* 367 */         arrayOfRef = this.objs;
/*     */       } 
/*     */       
/* 370 */       byte b1 = 0; byte b2;
/* 371 */       for (b2 = 0; b2 < this.keys.length; b2++) {
/* 372 */         if (this.objs[b2] != null) {
/* 373 */           arrayOfInt[b1] = this.keys[b2];
/* 374 */           arrayOfRef[b1] = this.objs[b2];
/* 375 */           b1++;
/*     */         } 
/*     */       } 
/* 378 */       for (b2 = b1; b2 < arrayOfInt.length; b2++) {
/* 379 */         arrayOfInt[b2] = 0;
/* 380 */         arrayOfRef[b2] = null;
/*     */       } 
/*     */       
/* 383 */       this.keys = arrayOfInt;
/* 384 */       this.objs = arrayOfRef;
/* 385 */       this.next = b1;
/*     */       
/* 387 */       if (this.live != this.next) {
/* 388 */         throw new RuntimeException("bad state: live=" + this.live + ", next=" + this.next);
/*     */       }
/*     */     }
/*     */     
/*     */     private static int roundPow2(int param1Int) {
/* 393 */       int i = 1;
/* 394 */       while (i < param1Int) {
/* 395 */         i *= 2;
/*     */       }
/* 397 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/lqx/Desktop/sm_crypto/classes.jar!/go/Seq.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */