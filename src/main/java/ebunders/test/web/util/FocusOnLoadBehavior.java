package ebunders.test.web.util;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;

public class FocusOnLoadBehavior extends AbstractTransformerBehavior
{
    private Component component;

    public void bind( Component component )
    {
        this.component = component;
        component.setOutputMarkupId(true);
    }

    @Override
    public CharSequence transform(Component component, CharSequence output) throws Exception {
        return "<script type=\"javascript\">document.getElementById('" + component.getMarkupId() + "').focus()</script>" + output;
    }
}