Forked from https://github.com/tpopela/vips_java - this is a JAVA implementation of the VIPS (Visual Internet Page Segmentation) algorithm by Microsoft (http://www.cad.zju.edu.cn/home/dengcai/VIPS/VIPS_July-2004.pdf).

The original implementation output a XML file that describe the visual block hierarchy of a given webpage. We added also an HTML output that is more easy to work with and can be presented in a browser as a tree. We also added a pre-processing of the examined web-page that marks all DOM nodes with special IDs, so that the blocks in the final output of the algorithm can be correlated to their original DOM elements. 

The implementation comes with a single UT that can be set with a specific web-site URL and run by maven (`mvn test`).
All output files will be written to an `evinced-output-<date>` folder:
1. VIPSResult.xml - original algorithm output in XML.
2. VIPSResult.html - our HTML output of the algorithm that can be viewed in a browser.
3. html-with-evinced-ids.txt - the original website HTML enriched with our IDS.
4. evinced-mark-VIPS-blocks - a generated script that once injected to the source website it wil mark the visual blocks found by VIPS in red.

# segmentation-Layout-Content-website
segmentation-Layout-Content-website
