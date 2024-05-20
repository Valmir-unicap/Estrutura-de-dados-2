public class Retorno <T extends Comparable <T>> { 
  private NodeB filhoDir;
  private boolean h;
  private T info;
  
  void setFilhoDir (NodeB f) { 
    this.filhoDir = f;
  }
  
  NodeB getFilhoDir () { 
    return this.filhoDir;
  }
  
  void setH (boolean h) { 
    this.h = h;
  }
  
  boolean getH () { 
    return this.h;
  }
  
  void setInfo (T info) { 
    this.info = info;
  }
  
  T getInfo () { 
    return this.info;
  }
}
