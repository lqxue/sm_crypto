/*     */ package sm_crypto;
/*     */ 
/*     */ import go.Seq;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ public final class SmCrypto
/*     */   implements Seq.Proxy
/*     */ {
/*     */   private final int refnum;
/*     */   
/*     */   static {
/*  13 */     Sm_crypto.touch();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int incRefnum() {
/*  18 */     Seq.incGoRef(this.refnum, (Seq.GoObject)this);
/*  19 */     return this.refnum;
/*     */   }
/*     */   SmCrypto(int paramInt) {
/*  22 */     this.refnum = paramInt; Seq.trackGoRef(paramInt, (Seq.GoObject)this);
/*     */   } public SmCrypto() {
/*  24 */     this.refnum = __New(); Seq.trackGoRef(this.refnum, (Seq.GoObject)this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  97 */     if (paramObject == null || !(paramObject instanceof SmCrypto)) {
/*  98 */       return false;
/*     */     }
/* 100 */     SmCrypto smCrypto = (SmCrypto)paramObject;
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Arrays.hashCode(new Object[0]);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 109 */     StringBuilder stringBuilder = new StringBuilder();
/* 110 */     stringBuilder.append("SmCrypto").append("{");
/* 111 */     return stringBuilder.append("}").toString();
/*     */   }
/*     */   
/*     */   private static native int __New();
/*     */   
/*     */   public native byte[] ecrecover(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) throws Exception;
/*     */   
/*     */   public native boolean verifySignature(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3);
/*     */ }


/* Location:              /Users/lqx/Desktop/sm_crypto/classes.jar!/sm_crypto/SmCrypto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */