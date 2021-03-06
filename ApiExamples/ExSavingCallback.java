//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import org.testng.annotations.Test;
import com.aspose.words.HtmlFixedSaveOptions;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.PdfSaveOptions;
import com.aspose.words.PsSaveOptions;
import com.aspose.words.SvgSaveOptions;
import com.aspose.words.XamlFixedSaveOptions;
import com.aspose.words.XpsSaveOptions;
import com.aspose.words.Document;
import org.testng.Assert;
import com.aspose.words.IPageSavingCallback;
import com.aspose.words.PageSavingArgs;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;
import java.util.regex.Pattern;

public class ExSavingCallback extends ApiExampleBase
{
    @Test
    public void checkThatAllMethodsArePresent()
    {
        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        htmlFixedSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());

        ImageSaveOptions imageSaveOptions = new ImageSaveOptions(SaveFormat.PNG);
        imageSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());

        PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
        pdfSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());

        PsSaveOptions psSaveOptions = new PsSaveOptions();
        psSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());

        SvgSaveOptions svgSaveOptions = new SvgSaveOptions();
        svgSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());

        XamlFixedSaveOptions xamlFixedSaveOptions = new XamlFixedSaveOptions();
        xamlFixedSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());

        XpsSaveOptions xpsSaveOptions = new XpsSaveOptions();
        xpsSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());
    }

    @Test
    public void pageFileNameSavingCallback() throws Exception
    {
        //ExStart
        //ExFor:IPageSavingCallback
        //ExFor:FixedPageSaveOptions.PageSavingCallback
        //ExSummary:Shows how separate pages are saved when a document is exported to fixed page format.
        Document doc = new Document(getMyDir() + "Rendering.doc");

        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        {
            htmlFixedSaveOptions.setPageIndex(0);
            htmlFixedSaveOptions.setPageCount(doc.getPageCount());
        }
        htmlFixedSaveOptions.setPageSavingCallback(new CustomPageFileNamePageSavingCallback());

        doc.save(getMyDir() + "\\Artifacts\\Rendering.html", htmlFixedSaveOptions);

        String[] filePaths = GetFiles(getMyDir() + "\\Artifacts\\", "Page_*.html");

        for (int i = 0; i < doc.getPageCount(); i++)
        {
            String file = MessageFormat.format(getMyDir() + "\\Artifacts\\Page_{0}.html", i);
            Assert.assertEquals(file, filePaths[i]);//ExSkip
        }
    }

    private static String[] GetFiles(final String path, final String searchPattern)
    {
        final Pattern re = Pattern.compile(searchPattern.replace("*", ".*").replace("?", ".?"));
        String[] filenames = new File(path).list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return new File(dir, name).isFile() && re.matcher(name).matches();
            }
        });
        for (int i = 0; i < filenames.length; i++)
        {
            filenames[i] = path + filenames[i];
        }
        return filenames;
    }

    /** 
     * Custom PageFileName is specified.
     */
    private static class CustomPageFileNamePageSavingCallback implements IPageSavingCallback
    {
        public void pageSaving(PageSavingArgs args) throws Exception
        {
            // Specify name of the output file for the current page.
            args.setPageFileName(MessageFormat.format(getMyDir() + "\\Artifacts\\Page_{0}.html", args.getPageIndex()));
        }
    }
    //ExEnd
}
