package dev.rgbmc.simpleinv.handlers;

import dev.rgbmc.simpleinv.objects.CloseState;
import java.util.function.Consumer;

public class CloseHandler {
  private final Consumer<CloseState> consumer;

  public CloseHandler(Consumer<CloseState> consumer) {
    this.consumer = consumer;
  }

  public void call(CloseState closeState) {
    consumer.accept(closeState);
  }
}
