import { InfiniteSliderBasic } from "@/components/InfiniteSliderBasic";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";

function SplashPage() {



  return (
    <>
      <h1>A 

        new/better/efficient/convenient/smart way to do</h1>
      <h1>LeetCode</h1>

      <Button asChild>
        <Link to="#">Get Started for Free</Link>
      </Button>
      
      
      
      <InfiniteSliderBasic />
    </>
  )
}

export default SplashPage;
