package org.tron.core.net.peer;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tron.common.overlay.message.Message;
import org.tron.common.overlay.server.Channel;
import org.tron.common.utils.Sha256Hash;
import org.tron.core.capsule.BlockCapsule.BlockId;

@Slf4j
@Component
@Scope("prototype")
public class PeerConnection extends Channel{

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  //broadcast
  private Queue<Sha256Hash> invToUs = new LinkedBlockingQueue<>();

  private Queue<Sha256Hash> invWeAdv = new LinkedBlockingQueue<>();

  private HashMap<Sha256Hash, Long> advObjSpreadToUs = new HashMap<>();

  private HashMap<Sha256Hash, Long> advObjWeSpread = new HashMap<>();

  private HashMap<Sha256Hash, Long> advObjWeRequested = new HashMap<>();

  public HashMap<Sha256Hash, Long> getAdvObjSpreadToUs() {
    return advObjSpreadToUs;
  }

  public void setAdvObjSpreadToUs(
      HashMap<Sha256Hash, Long> advObjSpreadToUs) {
    this.advObjSpreadToUs = advObjSpreadToUs;
  }

  public HashMap<Sha256Hash, Long> getAdvObjWeSpread() {
    return advObjWeSpread;
  }

  public void setAdvObjWeSpread(HashMap<Sha256Hash, Long> advObjWeSpread) {
    this.advObjWeSpread = advObjWeSpread;
  }

  //sync chain
  private BlockId headBlockWeBothHave;

  private long headBlockTimeWeBothHave;

  private Deque<BlockId> syncBlockToFetch = new LinkedList<>();

  private HashMap<BlockId, Long> syncBlockRequested = new HashMap<>();

  private Pair<LinkedList<BlockId>, Long> syncChainRequested = null;

  public Pair<LinkedList<BlockId>, Long> getSyncChainRequested() {
    return syncChainRequested;
  }

  public void setSyncChainRequested(
      Pair<LinkedList<BlockId>, Long> syncChainRequested) {
    this.syncChainRequested = syncChainRequested;
  }

  public HashMap<BlockId, Long> getSyncBlockRequested() {
    return syncBlockRequested;
  }

  public void setSyncBlockRequested(
      HashMap<BlockId, Long> syncBlockRequested) {
    this.syncBlockRequested = syncBlockRequested;
  }

  public long getUnfetchSyncNum() {
    return unfetchSyncNum;
  }

  public void setUnfetchSyncNum(long unfetchSyncNum) {
    this.unfetchSyncNum = unfetchSyncNum;
  }

  private long unfetchSyncNum = 0L;

  private boolean needSyncFromPeer;

  private boolean needSyncFromUs;

  public Set<BlockId> getBlockInProc() {
    return blockInProc;
  }

  public void setBlockInProc(Set<BlockId> blockInProc) {
    this.blockInProc = blockInProc;
  }

  private boolean banned;

  private Set<BlockId> blockInProc = new HashSet<>();

  public HashMap<Sha256Hash, Long> getAdvObjWeRequested() {
    return advObjWeRequested;
  }

  public void setAdvObjWeRequested(HashMap<Sha256Hash, Long> advObjWeRequested) {
    this.advObjWeRequested = advObjWeRequested;
  }


  public void cleanInvGarbage() {
    //TODO: clean advObjSpreadToUs and advObjWeSpread accroding cleaning strategy 

  }

  public boolean isBanned() {
    return banned;
  }

  public void setBanned(boolean banned) {
    this.banned = banned;
  }

  public BlockId getHeadBlockWeBothHave() {
    return headBlockWeBothHave;
  }

  public void setHeadBlockWeBothHave(BlockId headBlockWeBothHave) {
    this.headBlockWeBothHave = headBlockWeBothHave;
  }

  public long getHeadBlockTimeWeBothHave() {
    return headBlockTimeWeBothHave;
  }

  public void setHeadBlockTimeWeBothHave(long headBlockTimeWeBothHave) {
    this.headBlockTimeWeBothHave = headBlockTimeWeBothHave;
  }

  public Deque<BlockId> getSyncBlockToFetch() {
    return syncBlockToFetch;
  }

  public boolean isNeedSyncFromPeer() {
    return needSyncFromPeer;
  }

  public void setNeedSyncFromPeer(boolean needSyncFromPeer) {
    this.needSyncFromPeer = needSyncFromPeer;
  }

  public boolean isNeedSyncFromUs() {
    return needSyncFromUs;
  }

  public void setNeedSyncFromUs(boolean needSyncFromUs) {
    this.needSyncFromUs = needSyncFromUs;
  }

  public Queue<Sha256Hash> getInvToUs() {
    return invToUs;
  }

  public void setInvToUs(Queue<Sha256Hash> invToUs) {
    this.invToUs = invToUs;
  }

  public Queue<Sha256Hash> getInvWeAdv() {
    return invWeAdv;
  }

  public void setInvWeAdv(Queue<Sha256Hash> invWeAdv) {
    this.invWeAdv = invWeAdv;
  }


  public boolean isBusy() {
    return !advObjWeRequested.isEmpty()
        && !syncBlockRequested.isEmpty()
        && syncChainRequested != null;
  }

  public void sendMessage(Message message) {
    msgQueue.sendMessage(message);
    nodeStatistics.ethOutbound.add();
  }

  @Override
  public String toString() {
    return nodeStatistics.toString();
  }
}
