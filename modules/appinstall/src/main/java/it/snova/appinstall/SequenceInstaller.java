package it.snova.appinstall;

import it.snova.apptables.data.DomainTable;
import it.snova.apptables.data.GroupsTable;
import it.snova.apptables.data.UsersTable;
import it.snova.hbaselib.framework.HClient;
import it.snova.hbaselib.schema.SequenceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SequenceInstaller
{
  HClient client;
  List<SequenceBuilder> sequences;
  
  public SequenceInstaller(HClient client)
  {
    this.client = client;
    sequences = new ArrayList<SequenceBuilder>();
  }
  
  public SequenceInstaller addSequence(Class c) throws IOException
  {
    sequences.add(client.getZContext().getSequence(c));
    return this;
  }
  
  public SequenceInstaller addSequence(String name) throws IOException
  {
    sequences.add(client.getZContext().getSequence(name));
    return this;
  }

  public SequenceInstaller addSequence(String name, long startingIndex) throws IOException
  {
    sequences.add(client.getZContext().getSequence(name).withStartingIndex(startingIndex));
    return this;
  }
  
  public SequenceInstaller recreateSequences() throws Exception
  {
    for (SequenceBuilder sb: sequences) {
      recreateSequence(sb);
    }
    return this;
  }
  
  private void recreateSequence(SequenceBuilder sb) throws Exception
  {
    sb.delete().create();
  }

  static public SequenceInstaller install(HClient client) throws Exception
  {
    return new SequenceInstaller(client)
      .addSequence(DomainTable.class)
      .addSequence(UsersTable.class)
      .addSequence(GroupsTable.class)
      .recreateSequences();
  }

}
