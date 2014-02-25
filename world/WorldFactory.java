package sh.world;

public abstract interface WorldFactory
{
  public abstract WorldModel createWorldModel();
  
  public abstract WorldView createWorldView(WorldModel paramWorldModel);
  
  public abstract WorldController createWorldController(WorldModel paramWorldModel, WorldView paramWorldView);
}


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.WorldFactory
 * JD-Core Version:    0.7.0.1
 */