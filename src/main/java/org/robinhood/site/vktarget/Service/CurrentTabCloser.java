package org.robinhood.site.vktarget.Service;

import org.robinhood.site.OperationImage;

/**
 * Закрыть текущую вкладку браузера.
 */
public class CurrentTabCloser extends OperationImage {
    @Override
    public String key() {
        return "CurrentTabCloser";
    }
}
