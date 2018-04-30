package com.platform;


import com.breadwallet.tools.util.Utils;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BreadWallet
 * <p/>
 * Created by Mihail Gutan on <mihail@breadwallet.com> 2/16/17.
 * Copyright (c) 2017 breadwallet LLC
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class BRHTTPHelper {
    public static final String TAG = BRHTTPHelper.class.getName();

    public static boolean handleError(int err, String errMess, Request baseRequest, HttpServletResponse resp) {
        try {
            baseRequest.setHandled(true);
            if (Utils.isNullOrEmpty(errMess))
                resp.sendError(err);
            else
                resp.sendError(err, errMess);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean handleSuccess(APIClient.BRResponse brResp, Request baseRequest, HttpServletResponse resp) {
        try {
            if (brResp.getCode() == 0) throw new RuntimeException("http code can't be 0");
            resp.setStatus(brResp.getCode());
            if (!Utils.isNullOrEmpty(brResp.getContentType()))
                resp.setContentType(brResp.getContentType());
            else resp.setContentType("text/plain");
            if (!Utils.isNullOrEmpty(brResp.getBody()))
                resp.getOutputStream().write(brResp.getBody());
            for (String key : brResp.getHeaders().keySet()) {
                resp.setHeader(key, brResp.getHeaders().get(key));
            }
            baseRequest.setHandled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static byte[] getBody(HttpServletRequest request) {
        if (request == null) return null;
        byte[] rawData = null;
        try {
            InputStream body = request.getInputStream();
            rawData = IOUtils.toByteArray(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawData;
    }
}
