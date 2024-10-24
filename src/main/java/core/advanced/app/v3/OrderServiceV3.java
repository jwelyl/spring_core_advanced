package core.advanced.app.v3;

import core.advanced.trace.TraceStatus;
import core.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
  private final OrderRepositoryV3 orderRepository;
  private final LogTrace trace;

  public void orderItem(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderService.orderItem()");
      orderRepository.save(itemId);
      trace.end(status);
    }  catch (Exception e) {
      trace.exception(status, e);
      throw e;  //  예외를 꼭 다시 던져주어야 한다. (로그 기능 때문에 흐름을 바꿔선 안된다.)
    }
  }
}
