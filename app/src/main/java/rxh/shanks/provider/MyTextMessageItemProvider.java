package rxh.shanks.provider;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

/**
 * Created by Administrator on 2016/8/17.
 */

@ProviderTag(messageContent = TextMessage.class, showPortrait = false, centerInHorizontal = false)
public class MyTextMessageItemProvider extends TextMessageItemProvider {
}
