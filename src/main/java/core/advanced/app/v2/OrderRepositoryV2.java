package core.advanced.app.v2;

import core.advanced.trace.TraceId;
import core.advanced.trace.TraceStatus;
import core.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {
  private final HelloTraceV2 trace;

  public void save(TraceId traceId, String itemId) {
    TraceStatus status = null;

    try {
      status = trace.beginSync(traceId, "OrderRepository.save()");

      //  저장 로직
      if(itemId.equals("ex")) {
        throw new IllegalStateException("예외 발생!");
      }

      sleep(1000);

      trace.end(status);
    }  catch (Exception e) {
      trace.exception(status, e);
      throw e;  //  예외를 꼭 다시 던져주어야 한다. (로그 기능 때문에 흐름을 바꿔선 안된다.)
    }
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
